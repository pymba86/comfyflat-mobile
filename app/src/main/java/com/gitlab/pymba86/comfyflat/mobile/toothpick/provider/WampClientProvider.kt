package com.gitlab.pymba86.comfyflat.mobile.toothpick.provider

import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import ws.wamp.jawampa.WampClient
import ws.wamp.jawampa.WampClientBuilder
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider
import javax.inject.Inject
import javax.inject.Provider

class WampClientProvider @Inject constructor(
    private val session: Session
) : Provider<WampClient> {

    override fun get() = WampClientBuilder().apply {
        withConnectorProvider(NettyWampClientConnectorProvider())
        withUri(session.url)
        withRealm(session.realm)
    }.build()

}