package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.extension.argument
import com.gitlab.pymba86.comfyflat.mobile.extension.setLaunchScreen
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomId
import com.gitlab.pymba86.comfyflat.mobile.toothpick.RoomName
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.FlowNavigationModule
import com.gitlab.pymba86.comfyflat.mobile.ui.global.FlowFragment
import ru.terrakok.cicerone.Router
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

class RoomFlowFragment : FlowFragment(), MvpView {


    private val roomId by argument(ARG_ROOM_ID, 0)
    private val roomName by argument(ARG_ROOM_NAME, "")

    @Inject
    lateinit var router: Router

    override val parentScopeName = DI.SERVER_SCOPE


    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java)),
            object : Module() {
                init {
                    bind(PrimitiveWrapper::class.java)
                        .withName(RoomId::class.java)
                        .toInstance(PrimitiveWrapper(roomId))

                    bind(PrimitiveWrapper::class.java)
                        .withName(RoomName::class.java)
                        .toInstance(PrimitiveWrapper(roomName))
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.RoomMainFlow)
        }
    }

    override fun onExit() {
        router.exit()
    }


    companion object {
        private const val ARG_ROOM_ID = "arg_room_id"
        private const val ARG_ROOM_NAME = "arg_room_name"
        fun create(roomId: Int, roomName: String) =
            RoomFlowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ROOM_ID, roomId)
                    putString(ARG_ROOM_NAME, roomName)
                }
            }
    }
}