package com.gitlab.pymba86.comfyflat.mobile.model.interactor.launch

import com.gitlab.pymba86.comfyflat.mobile.model.repository.session.SessionRepository
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.ServerModule
import toothpick.Toothpick
import javax.inject.Inject

class LaunchInteractor @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    fun signInToSession(): Boolean {
        val session = sessionRepository.getCurrentSession()
        Toothpick.closeScope(DI.SERVER_SCOPE)
        Toothpick
            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .installModules(ServerModule(session))
        return session != null
    }
}