package com.gitlab.pymba86.comfyflat.mobile.presentation.drawer

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.Session

@StateStrategyType(AddToEndSingleStrategy::class)
interface NavigationDrawerView : MvpView {
    enum class MenuItem {
        ROOMS,
        ABOUT
    }

    fun selectMenuItem(item: MenuItem)
    fun setSessions(sessions: List<Session>, currentSession: Session)
}