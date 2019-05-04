package com.gitlab.pymba86.comfyflat.mobile.toothpick.client

import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.Observable
import ws.wamp.jawampa.WampClient
import ws.wamp.jawampa.WampClientBuilder
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Wamp @Inject constructor(
    private val session: Session,
    private val schedulers: SchedulersProvider
)  {

    lateinit var client: WampClient
    private val stateRelay = BehaviorRelay.create<WampState>()
    val state: Observable<WampState> = stateRelay

    init {
        try {
            val builder = WampClientBuilder().apply {
                withConnectorProvider(NettyWampClientConnectorProvider())
                withUri(session.url)
                withRealm(session.realm)
                withInfiniteReconnects()
                withReconnectInterval(3, TimeUnit.SECONDS)
            }
            client = builder.build()

            val subscribe = RxJavaInterop.toV2Observable(client.statusChanged())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    { state ->
                        when (state) {
                            is WampClient.ConnectedState -> this.stateRelay.accept(WampState.OPEN)
                            is WampClient.ConnectingState -> this.stateRelay.accept(WampState.CONNECTING)
                            is WampClient.DisconnectedState -> this.stateRelay.accept(WampState.CLOSED)
                        }
                    },
                    { it.printStackTrace() }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}