package com.gitlab.pymba86.comfyflat.mobile

import com.gitlab.pymba86.comfyflat.mobile.ui.about.AboutFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.develop.DevelopFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.drawer.DrawerFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.main.MainFlowFragment
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

    object Develop : SupportAppScreen() {
        override fun getFragment() = DevelopFragment()
    }

    object UserInfo : SupportAppScreen() {
        override fun getFragment() = UserInfoFragment()
    }

    object About : SupportAppScreen() {
        override fun getFragment() = AboutFragment()
    }

    object Rooms : SupportAppScreen() {
        override fun getFragment() = RoomsListFragment()
    }

}