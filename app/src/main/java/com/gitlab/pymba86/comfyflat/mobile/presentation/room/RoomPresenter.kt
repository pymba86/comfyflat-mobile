package com.gitlab.pymba86.comfyflat.mobile.presentation.room

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.device.DeviceInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomId
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomName
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
    }

    fun onBackPressed() = flowRouter.exit()
}