package com.gitlab.pymba86.comfyflat.mobile.presentation.rooms

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gitlab.pymba86.comfyflat.mobile.entity.Room

@StateStrategyType(AddToEndSingleStrategy::class)
interface RoomsListView : MvpView {
    fun showRefreshProgress(show: Boolean)
    fun showEmptyProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
    fun showEmptyView(show: Boolean)
    fun showEmptyError(show: Boolean, message: String?)
    fun showProjects(show: Boolean, projects: List<Room>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}