package com.gitlab.pymba86.comfyflat.mobile.ui.user

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.extension.argument
import com.gitlab.pymba86.comfyflat.mobile.extension.setLaunchScreen
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.UserId
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.FlowNavigationModule
import com.gitlab.pymba86.comfyflat.mobile.ui.global.FlowFragment
import ru.terrakok.cicerone.Router
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

class UserFlowFragment : FlowFragment(), MvpView {

    private val userId by argument(ARG_USER_ID, 0L)

    override val parentScopeName = DI.APP_SCOPE

    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java)),
            object : Module() {
                init {
                    bind(PrimitiveWrapper::class.java)
                        .withName(UserId::class.java)
                        .toInstance(PrimitiveWrapper(userId))
                }
            }
        )
    }

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.UserInfo)
        }
    }

    override fun onExit() {
        router.exit()
    }

    companion object {
        private const val ARG_USER_ID = "arg_user_id"
        fun create(userId: Long) =
            UserFlowFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_USER_ID, userId)
                }
            }
    }
}