package com.gitlab.pymba86.comfyflat.mobile

import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.drawer.DrawerFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.main.MainFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsContainerFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsListFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.user.UserFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.user.info.UserInfoFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object DrawerFlow : SupportAppScreen() {
        override fun getFragment() = DrawerFlowFragment()
    }

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object Auth : SupportAppScreen() {
        override fun getFragment() = AuthFragment()
    }

    data class UserFlow(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment() = UserFlowFragment.create(userId)
    }

    object UserInfo : SupportAppScreen() {
        override fun getFragment() = UserInfoFragment()
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