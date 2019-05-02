package com.gitlab.pymba86.comfyflat.mobile.presentation.room

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.device.DeviceInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.Paginator
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomId
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomName
import io.reactivex.Single
import javax.inject.Inject

@InjectViewState
class RoomPresenter @Inject constructor(
    @RoomId private val roomIdWrapper: PrimitiveWrapper<Long>,
    @RoomName private val roomNameWrapper: PrimitiveWrapper<String>,
    private val deviceInteractor: DeviceInteractor,
    private val flowRouter: FlowRouter
    ) : BasePresenter<RoomView>(){

    private val roomId = roomIdWrapper.value
    private val roomName = roomNameWrapper.value

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setTitle(roomName)

        refreshRoomDevices()
    }

    private val paginator = Paginator(
        { geRoomDevicesSingle(it) },
        object : Paginator.ViewController<Device> {
            override fun showEmptyProgress(show: Boolean) {
                viewState.showEmptyProgress(show)
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                if (error != null) {
                } else {
                    viewState.showEmptyError(show, null)
                }
            }

            override fun showErrorMessage(error: Throwable) {

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

    fun refreshRoomDevices() {
        paginator.refresh()
    }

    private fun geRoomDevicesSingle(page: Int) = Single.fromCallable {deviceInteractor.getDevices(page) }

    fun loadNextLabelsPage() = paginator.loadNewPage()

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

    fun onBackPressed() = flowRouter.exit()
}