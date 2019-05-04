package com.gitlab.pymba86.comfyflat.mobile.presentation

import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.launch.LaunchInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    launchInteractor: LaunchInteractor,
    private val router: Router
) {

    private val isSignedIn = launchInteractor.signInToSession()

    fun coldStart() {

        val rootScreen =
            if (isSignedIn) Screens.DrawerFlow
            else Screens.AuthFlow

        router.newRootScreen(rootScreen)

    }
}