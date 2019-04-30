package com.gitlab.pymba86.comfyflat.mobile.toothpick.module

import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import toothpick.config.Module

class GlobalMenuModule : Module() {
    init {
        bind(GlobalMenuController::class.java).toInstance(GlobalMenuController())
    }
}