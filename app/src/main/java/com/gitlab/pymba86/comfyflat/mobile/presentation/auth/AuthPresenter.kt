package com.gitlab.pymba86.comfyflat.mobile.presentation.auth

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import javax.inject.Inject

@InjectViewState
class AuthPresenter @Inject constructor(
    private val router: FlowRouter
    ) : BasePresenter<AuthView>() {


    fun onBackPressed() = router.exit()

    fun loginOnCustomServer(url: String, token: String) {

    }
}