package com.gitlab.pymba86.comfyflat.mobile

import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsContainerFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object Auth : SupportAppScreen() {
        override fun getFragment() = AuthFragment()
    }

    object RoomsContainer : SupportAppScreen() {
        override fun getFragment() = RoomsContainerFragment()
    }

    data class Rooms(
        val mode: Int
    ) : SupportAppScreen() {
        override fun getFragment() = RoomsListFragment.create(mode)
    }

}