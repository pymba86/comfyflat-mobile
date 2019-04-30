package com.gitlab.pymba86.comfyflat.mobile.presentation.drawer

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.UserAccount
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
    private var userAccount: UserAccount? =  null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        sessionInteractor.getCurrentUserAccount()?.let { acc ->
            this.userAccount = acc
            viewState.setAccounts(sessionInteractor.getUserAccounts(), acc)
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
                ROOMS -> router.newRootScreen(Screens.MainFlow)
                ABOUT -> router.newRootScreen(Screens.MainFlow)
            }
        }
    }

    fun onLogoutClick() {
        menuController.close()

    }

    fun onUserClick() {
        menuController.close()
        userAccount?.let {
            router.startFlow(Screens.UserFlow(it.userId))
        }
    }

    fun onAddAccountClick() {
        router.startFlow(Screens.AuthFlow)

    }

    fun onAccountClick(account: UserAccount) {
        if (account != userAccount) {
            sessionInteractor.setCurrentUserAccount(account.userId)?.let { acc ->
                router.newRootFlow(Screens.DrawerFlow)
            }
        }
    }
}