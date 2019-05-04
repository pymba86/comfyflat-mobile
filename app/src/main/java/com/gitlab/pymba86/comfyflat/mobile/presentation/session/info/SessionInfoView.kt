package com.gitlab.pymba86.comfyflat.mobile.presentation.session.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.Session

@StateStrategyType(AddToEndSingleStrategy::class)
interface SessionInfoView : MvpView {
    fun showSession(session: Session)
    fun showProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(msg: String)
}