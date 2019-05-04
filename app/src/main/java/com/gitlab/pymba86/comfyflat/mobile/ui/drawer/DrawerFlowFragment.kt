package com.gitlab.pymba86.comfyflat.mobile.ui.drawer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.extension.setLaunchScreen
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.Wamp
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.WampState
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.FlowNavigationModule
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.GlobalMenuModule
import com.gitlab.pymba86.comfyflat.mobile.ui.about.AboutFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsListFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.drawer_flow_fragment.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class DrawerFlowFragment : BaseFragment() {

    @Inject
    lateinit var wampClient: Wamp

    @Inject
    lateinit var menuController: GlobalMenuController

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private var menuStateDisposable: Disposable? = null

    private var wampClientStateDisposable: Disposable? = null

    override val layoutRes = R.layout.drawer_flow_fragment

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.mainContainer) as? BaseFragment

    private val drawerFragment
        get() = childFragmentManager.findFragmentById(R.id.navDrawerContainer) as? NavigationDrawerFragment


    override val parentScopeName = DI.SERVER_SCOPE

    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java)),
            GlobalMenuModule()
        )
    }

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this.activity, childFragmentManager, R.id.mainContainer) {

            override fun applyCommands(commands: Array<out Command>?) {
                super.applyCommands(commands)
                updateNavDrawer()
            }

            override fun activityBack() {
                router.exit()
            }

            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)

        if (childFragmentManager.fragments.isEmpty()) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.navDrawerContainer, NavigationDrawerFragment())
                .commitNow()

            navigator.setLaunchScreen(Screens.Rooms)
        } else {
            updateNavDrawer()
        }

        wampClient.client.open()
    }

    override fun onResume() {
        super.onResume()
        menuStateDisposable = menuController.state.subscribe { openNavDrawer(it) }
        wampClientStateDisposable = wampClient.state.subscribe { updateStateWampClient(it) }
        navigatorHolder.setNavigator(navigator)
    }

    private fun updateStateWampClient(state: WampState?) {
        drawerFragment?.let { drawerFragment ->
            drawerFragment.updateStateWampClient(state)
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        menuStateDisposable?.dispose()
        wampClientStateDisposable?.dispose()
        super.onPause()
    }

    //region nav drawer
    private fun openNavDrawer(open: Boolean) {
        if (open) drawerLayout.openDrawer(GravityCompat.START)
        else drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun updateNavDrawer() {
        childFragmentManager.executePendingTransactions()

        drawerFragment?.let { drawerFragment ->
            currentFragment?.let {
                when (it) {
                    is RoomsListFragment -> drawerFragment.onScreenChanged(NavigationDrawerView.MenuItem.ROOMS)
                    is AboutFragment -> drawerFragment.onScreenChanged(NavigationDrawerView.MenuItem.ABOUT)
                }
            }
        }
    }
    //endregion

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            openNavDrawer(false)
        } else {
            currentFragment?.onBackPressed() ?: router.exit()
        }
    }
}