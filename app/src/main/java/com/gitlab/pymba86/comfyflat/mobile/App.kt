package com.gitlab.pymba86.comfyflat.mobile

import android.app.Application
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.AppModule
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appCode = UUID.randomUUID().toString()

        initLogger()
        initToothpick()
        initAppScope()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppScope() {
        Toothpick.openScopes(DI.APP_SCOPE)
            .installModules(AppModule(this))
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
    }

    companion object {
        lateinit var appCode: String
            private set
    }
}