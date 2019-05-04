package com.gitlab.pymba86.comfyflat.mobile.model.repository.session


import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.data.storage.Prefs
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val prefs: Prefs
) {

    fun getSessions(): List<Session> =
        prefs.sessions.values.toList()

    fun getCurrentSession(): Session? {
        prefs.selectedSession?.let { id ->
            return prefs.sessions[id]
        }
        return null
    }

    fun remove(sessionId: String): Session? {
        val newSessions = prefs.sessions
        newSessions.remove(sessionId)
        prefs.sessions = newSessions

        val currentSession = prefs.selectedSession
        if (currentSession == sessionId) {
            val newSession = newSessions.values.firstOrNull()
            prefs.selectedSession = newSession?.id
            return newSession
        } else {
            return newSessions[currentSession]
        }
    }

    fun setCurrentSession(sessionId: String): Session? {
        val session = prefs.sessions[sessionId]
        prefs.selectedSession = session?.id
        return session
    }

    public fun saveNewSession(session : Session) {
        val newSessions = prefs.sessions
        newSessions.remove(session.id)
        newSessions[session.id] = session
        prefs.selectedSession = session.id
        prefs.sessions = newSessions
    }
}