package com.gitlab.pymba86.comfyflat.mobile.model.repository.app

import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppInfo
import com.gitlab.pymba86.comfyflat.mobile.model.data.storage.RawAppData
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class AppInfoRepository @Inject constructor(
    private val appInfo: AppInfo,
    private val schedulers: SchedulersProvider,
    private val rawAppData: RawAppData
) {
    fun getAppInfo() = Single.just(appInfo)

    fun getAppDevelopers() = rawAppData
        .getAppDevelopers()
        .onErrorReturn { emptyList() }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}