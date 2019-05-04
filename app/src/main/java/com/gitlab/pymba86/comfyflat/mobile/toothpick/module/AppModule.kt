package com.gitlab.pymba86.comfyflat.mobile.toothpick.module

import android.content.Context
import android.content.res.AssetManager
import com.gitlab.pymba86.comfyflat.mobile.BuildConfig
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppInfo
import com.gitlab.pymba86.comfyflat.mobile.model.data.storage.RawAppData
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.app.AppInfoInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.repository.app.AppInfoRepository
import com.gitlab.pymba86.comfyflat.mobile.model.repository.session.SessionRepository
import com.gitlab.pymba86.comfyflat.mobile.model.system.AppSchedulers
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.gitlab.pymba86.comfyflat.mobile.presentation.AppLauncher
import com.gitlab.pymba86.comfyflat.mobile.toothpick.provider.GsonProvider
import com.gitlab.pymba86.comfyflat.mobile.toothpick.provider.MoshiProvider
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        // Global
        bind(Context::class.java).toInstance(context)
        bind(AssetManager::class.java).toInstance(context.assets)
        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingletonInScope()
        bind(Moshi::class.java).toProvider(MoshiProvider::class.java).providesSingletonInScope()
        bind(RawAppData::class.java)
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())

        // Navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)


        // Session
        bind(SessionRepository::class.java).singletonInScope()

        //AppInfo
        bind(AppInfo::class.java).toInstance(
            AppInfo(
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE
            )
        )
        bind(AppInfoRepository::class.java)
        bind(AppInfoInteractor::class.java)

        bind(AppLauncher::class.java).singletonInScope()
    }
}