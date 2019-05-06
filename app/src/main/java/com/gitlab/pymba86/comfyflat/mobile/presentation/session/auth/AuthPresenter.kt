package com.gitlab.pymba86.comfyflat.mobile.presentation.session.auth

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.session.SessionInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import hu.akarnokd.rxjava.interop.RxJavaInterop
import ws.wamp.jawampa.WampClient
import ws.wamp.jawampa.WampClientBuilder
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider
import javax.inject.Inject

@InjectViewState
class AuthPresenter @Inject constructor(
    private val router: FlowRouter,
    private val schedulers: SchedulersProvider,
    private val sessionInteractor: SessionInteractor
    ) : BasePresenter<AuthView>() {


    fun onBackPressed() = router.exit()

    fun connectSession(url: String, realm: String) {

        try {

            lateinit var client: WampClient

            val builder = WampClientBuilder().apply {
                withConnectorProvider(NettyWampClientConnectorProvider())
                withUri(url)
                withRealm(realm)
            }
            client = builder.build()

            val subscribe = RxJavaInterop.toV2Observable(client.statusChanged())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    { state ->
                        when (state) {
                            is WampClient.ConnectedState ->  {
                                client.close().toBlocking().last()
                                val session = Session(url, realm)
                                viewState.showMessage("Успешно!")
                                sessionInteractor.saveNewSession(session)
                                sessionInteractor.switchSession(session)
                                router.newRootFlow(Screens.DrawerFlow)
                            }
                            is WampClient.ConnectingState -> {
                                viewState.showMessage("Идет подключение...")
                            }
                            is WampClient.DisconnectedState -> {
                               // viewState.showMessage("Подключение разорвано!")
                            }
                        }
                    },
                    { it.printStackTrace() }
                )
            client.open()

        } catch (e: Exception) {
            viewState.showMessage("Поля заполнены неверно!")
        }
    }
}