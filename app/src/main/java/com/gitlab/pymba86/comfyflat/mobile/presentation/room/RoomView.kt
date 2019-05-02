package com.gitlab.pymba86.comfyflat.mobile.presentation.room

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device

@StateStrategyType(AddToEndSingleStrategy::class)
interface RoomView : MvpView {

    fun setTitle(title: String)
    fun showDevices(show: Boolean, projects: List<Device>)
}