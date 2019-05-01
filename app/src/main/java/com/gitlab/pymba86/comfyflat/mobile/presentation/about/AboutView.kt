package com.gitlab.pymba86.comfyflat.mobile.presentation.about

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppInfo

@StateStrategyType(AddToEndSingleStrategy::class)
interface AboutView : MvpView {
    fun showAppInfo(appInfo: AppInfo)
}