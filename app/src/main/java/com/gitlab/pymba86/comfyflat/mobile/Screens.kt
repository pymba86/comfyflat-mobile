package com.gitlab.pymba86.comfyflat.mobile

import com.gitlab.pymba86.comfyflat.mobile.ui.about.AboutFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.session.auth.SessionAuthFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.session.auth.SessionAuthFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.develop.DevelopFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.drawer.DrawerFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.room.RoomFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.room.RoomFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.rooms.RoomsListFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.session.info.SessionFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.session.info.SessionInfoFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object DrawerFlow : SupportAppScreen() {
        override fun getFragment() = DrawerFlowFragment()
    }

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = SessionAuthFlowFragment()
    }

    object UserFlow : SupportAppScreen() {
        override fun getFragment() = SessionFlowFragment()
    }

    object Auth : SupportAppScreen() {
        override fun getFragment() = SessionAuthFragment()

    }

    object Develop : SupportAppScreen() {
        override fun getFragment() = DevelopFragment()
    }

    object UserInfo : SupportAppScreen() {
        override fun getFragment() = SessionInfoFragment()
    }

    object About : SupportAppScreen() {
        override fun getFragment() = AboutFragment()
    }

    object Rooms : SupportAppScreen() {
        override fun getFragment() = RoomsListFragment()
    }

    object RoomMainFlow : SupportAppScreen() {
        override fun getFragment() = RoomFragment()
    }

    data class RoomFlow(
        val roomId: Int, val roomName: String
    ) : SupportAppScreen() {
        override fun getFragment() = RoomFlowFragment.create(roomId, roomName)
    }
}