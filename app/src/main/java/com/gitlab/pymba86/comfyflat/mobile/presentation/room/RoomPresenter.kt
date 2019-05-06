package com.gitlab.pymba86.comfyflat.mobile.presentation.room

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCallFunction
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.device.DeviceInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.device.DeviceView
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.Paginator
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomId
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomName
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.WampState
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class RoomPresenter @Inject constructor(
    @RoomId private val roomIdWrapper: PrimitiveWrapper<Int>,
    @RoomName private val roomNameWrapper: PrimitiveWrapper<String>,
    private val deviceInteractor: DeviceInteractor,
    private val flowRouter: FlowRouter
) : BasePresenter<RoomView>() {

    private val roomId = roomIdWrapper.value
    private val roomName = roomNameWrapper.value

    private var wampClientStateDisposable: Disposable? = null
    private var deviceDataWampClientDisposable: Disposable? = null

    // Список подписанных устройств
    private var subscriptionViewDevices: HashMap<Int, DeviceView> = HashMap()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setTitle(roomName)

        wampClientStateDisposable = deviceInteractor.stateWampClient()
            .subscribe { state ->
                when (state) {
                    WampState.CONNECTING -> {
                        viewState.showEmptyProgress(true)
                    }
                    WampState.CLOSED -> {
                        deviceDataWampClientDisposable?.dispose()
                    }
                    WampState.OPEN -> {

                        // Подписываемся на получение новых данных
                        deviceDataWampClientDisposable = deviceInteractor.stateFunctionChange()
                            .subscribe (
                                { updateDataDeviceView(it) },
                                {  viewState.showEmptyError(true, "Не удалось обновить значение") })

                        // Загружаем список устройств
                        refreshRoomDevices()
                    }
                }
            }
    }


    fun publishDataDevice(data: DeviceCallFunction) {

        deviceInteractor.callFunctionDevice(data)
            .subscribe(
                {  },
                { viewState.showEmptyError(true, "Не удалось установить значение") }
            ).connect()
    }

    fun subscribeDeviceView(deviceId: Int, deviceView: DeviceView) {
        subscriptionViewDevices[deviceId] = deviceView
    }

    private fun updateDataDeviceView(data: DeviceCallFunction) {
        subscriptionViewDevices[data.idDevice]?.setData(data)
    }

    private val paginator = Paginator(
        { page -> deviceInteractor.getRoomDevices(roomId, page) },
        object : Paginator.ViewController<Device> {
            override fun showEmptyProgress(show: Boolean) {
                viewState.showEmptyProgress(show)
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.showEmptyProgress(!show)
                if (error != null) {
                    viewState.showEmptyError(show, "Не удалось подключиться")
                } else {
                    viewState.showEmptyError(show, null)
                }
            }

            override fun showErrorMessage(error: Throwable) {
                viewState.showEmptyProgress(false)
                viewState.showEmptyError(true, "Не удалось получить данные")
            }

            override fun showEmptyView(show: Boolean) {
                viewState.showEmptyView(show)
            }

            override fun showData(show: Boolean, data: List<Device>) {
                viewState.showDevices(show, data)
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showRefreshProgress(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPageProgress(show)
            }
        }
    )

    fun refreshRoomDevices() = paginator.refresh()

    fun loadNextLabelsPage() = paginator.loadNewPage()

    override fun onDestroy() {
        super.onDestroy()
        wampClientStateDisposable?.dispose()
        deviceDataWampClientDisposable?.dispose()
        paginator.release()
    }

    fun onBackPressed() = flowRouter.exit()
}