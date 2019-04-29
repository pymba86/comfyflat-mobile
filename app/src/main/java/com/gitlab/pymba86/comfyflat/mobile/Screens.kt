package com.gitlab.pymba86.comfyflat.mobile

import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFlowFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.auth.AuthFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object Auth : SupportAppScreen() {
        override fun getFragment() = AuthFragment()
    }
}