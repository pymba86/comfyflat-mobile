package com.gitlab.pymba86.comfyflat.mobile.presentation.device

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCallFunction

@StateStrategyType(AddToEndSingleStrategy::class)
interface DeviceView : MvpView {

    fun setData(funcLite: DeviceCallFunction)
}