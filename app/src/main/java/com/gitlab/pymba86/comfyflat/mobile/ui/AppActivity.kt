package com.gitlab.pymba86.comfyflat.mobile.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.presentation.AppLauncher
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var appLauncher: AppLauncher

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)

        if (savedInstanceState == null) {
            appLauncher.coldStart()
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}