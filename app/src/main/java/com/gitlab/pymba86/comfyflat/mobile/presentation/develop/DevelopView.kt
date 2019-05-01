package com.gitlab.pymba86.comfyflat.mobile.presentation.develop

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppDeveloper

@StateStrategyType(AddToEndSingleStrategy::class)
interface DevelopView : MvpView {
    fun showDevelopers(libraries: List<AppDeveloper>)
}