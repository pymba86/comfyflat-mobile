package com.gitlab.pymba86.comfyflat.mobile.ui.session.auth

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.extension.setLaunchScreen
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.FlowNavigationModule
import com.gitlab.pymba86.comfyflat.mobile.ui.global.FlowFragment
import ru.terrakok.cicerone.Router
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class SessionAuthFlowFragment : FlowFragment(), MvpView {

    override val parentScopeName = DI.SERVER_SCOPE

    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java))
        )
    }

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.Auth)
        }
    }

    override fun onExit() {
        router.exit()
    }
}