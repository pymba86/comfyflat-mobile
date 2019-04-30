package com.gitlab.pymba86.comfyflat.mobile.presentation

import com.gitlab.pymba86.comfyflat.mobile.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val router: Router
) {

    fun coldStart() {
        router.newRootScreen(Screens.RoomsContainer)

    }
}