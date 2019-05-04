package com.gitlab.pymba86.comfyflat.mobile.toothpick.module


import com.gitlab.pymba86.comfyflat.mobile.BuildConfig
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.room.RoomInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.repository.room.RoomRepository
import com.gitlab.pymba86.comfyflat.mobile.model.repository.session.SessionRepository
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.Wamp
import toothpick.config.Module

class ServerModule(session: Session?) : Module() {

    init {
        val newSession = session ?: Session(BuildConfig.SESSION_URL, BuildConfig.SESSION_REALM)

        // Session
        bind(Session::class.java).toInstance(newSession)
        bind(Wamp::class.java).singletonInScope()
        bind(SessionRepository::class.java).singletonInScope()

        // Room
        bind(RoomRepository::class.java)
        bind(RoomInteractor::class.java)
    }
}