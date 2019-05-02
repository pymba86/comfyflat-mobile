package com.gitlab.pymba86.comfyflat.mobile.toothpick.module

import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.Session
import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.UserAccount
import com.gitlab.pymba86.comfyflat.mobile.toothpick.provider.WampClientProvider
import toothpick.config.Module
import ws.wamp.jawampa.WampClient

class ServerModule() : Module() {

    init {

        // Session Data
        bind(Session::class.java).toInstance(Session("ws://127.0.0.1:55555", "demo"))

        // Session
       // bind(WampClient::class.java).toProvider(WampClientProvider::class.java).providesSingletonInScope()
    }
}