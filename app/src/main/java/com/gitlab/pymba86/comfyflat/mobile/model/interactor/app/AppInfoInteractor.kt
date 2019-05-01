package com.gitlab.pymba86.comfyflat.mobile.model.interactor.app

import com.gitlab.pymba86.comfyflat.mobile.model.repository.app.AppInfoRepository
import javax.inject.Inject

class AppInfoInteractor @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) {

    fun getAppInfo() = appInfoRepository.getAppInfo()
    fun getAppDevelopers() = appInfoRepository.getAppDevelopers()
}