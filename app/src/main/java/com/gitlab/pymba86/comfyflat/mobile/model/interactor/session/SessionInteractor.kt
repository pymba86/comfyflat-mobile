package com.gitlab.pymba86.comfyflat.mobile.model.interactor.session

import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.repository.session.SessionRepository
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.toothpick.module.ServerModule
import toothpick.Toothpick
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    fun getSessions() = sessionRepository.getSessions()
    fun getCurrentSession() = sessionRepository.getCurrentSession()

    fun setCurrentSession(sessionId: String): Session? {
        val newSession = sessionRepository.setCurrentSession(sessionId)
        switchSession(newSession)
        return newSession
    }

    fun remove(): Boolean {
        val currentSession = sessionRepository.getCurrentSession()
        if (currentSession != null) {
            return remove(currentSession.id)
        } else {
            return false
        }
    }

    fun remove(sessionId: String): Boolean {
        val newSession = sessionRepository.remove(sessionId)
        switchSession(newSession)
        return newSession != null
    }


    fun switchSession(newSession: Session?) {
        Toothpick.closeScope(DI.SERVER_SCOPE)
        Toothpick
            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .installModules(ServerModule(newSession))
    }

    fun saveNewSession(session : Session) = sessionRepository.saveNewSession(session)

}