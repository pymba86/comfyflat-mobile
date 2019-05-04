package com.gitlab.pymba86.comfyflat.mobile.presentation.drawer

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.session.SessionInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem.*
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import javax.inject.Inject

@InjectViewState
class NavigationDrawerPresenter @Inject constructor(
    private val router: FlowRouter,
    private val menuController: GlobalMenuController,
    private val sessionInteractor: SessionInteractor
) : BasePresenter<NavigationDrawerView>() {

    private var currentSelectedItem: MenuItem? = null
    private var currentSession: Session? =  null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        sessionInteractor.getCurrentSession()?.let { item ->
            this.currentSession = item
            viewState.setSessions(sessionInteractor.getSessions(), item)
        }

    }

    fun onScreenChanged(item: MenuItem) {
        menuController.close()
        currentSelectedItem = item
        viewState.selectMenuItem(item)
    }

    fun onMenuItemClick(item: MenuItem) {
        menuController.close()
        if (item != currentSelectedItem) {
            when (item) {
                ROOMS -> router.newRootScreen(Screens.Rooms)
                ABOUT -> router.newRootScreen(Screens.About)
            }
        }
    }

    fun onRemoveSessionClick() {
        menuController.close()
        currentSession?.let {
            val hasOtherSession = sessionInteractor.remove(it.id)
            if (hasOtherSession) {
                router.newRootFlow(Screens.DrawerFlow)
            } else {
                router.newRootFlow(Screens.AuthFlow)
            }
        }
    }

    fun onSessionClick() {
        menuController.close()
        currentSession?.let {
            router.startFlow(Screens.UserFlow)
        }
    }

    fun onAddSessionClick() {
        router.startFlow(Screens.AuthFlow)

    }

    fun onSessionClick(session: Session) {
        if (session != currentSession) {
            sessionInteractor.setCurrentSession(session.id)?.let {
                router.newRootFlow(Screens.DrawerFlow)
            }
        }
    }
}