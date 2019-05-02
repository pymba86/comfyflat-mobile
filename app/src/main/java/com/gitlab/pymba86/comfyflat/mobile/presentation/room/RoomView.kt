package com.gitlab.pymba86.comfyflat.mobile.presentation.room

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device

@StateStrategyType(AddToEndSingleStrategy::class)
interface RoomView : MvpView {

    fun showRefreshProgress(show: Boolean)
    fun showEmptyProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
    fun showEmptyView(show: Boolean)
    fun showEmptyError(show: Boolean, message: String?)

    fun setTitle(title: String)
    fun showDevices(show: Boolean, devices: List<Device>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}