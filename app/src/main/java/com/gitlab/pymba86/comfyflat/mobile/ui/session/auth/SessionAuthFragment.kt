package com.gitlab.pymba86.comfyflat.mobile.ui.session.auth

import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.extension.showSnackMessage
import com.gitlab.pymba86.comfyflat.mobile.presentation.session.auth.AuthPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.session.auth.AuthView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import ws.wamp.jawampa.internal.UriValidator
import java.net.URI

class SessionAuthFragment : BaseFragment(), AuthView {

    override val layoutRes = R.layout.fragment_auth
    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun providePresenter(): AuthPresenter =
        scope.getInstance(AuthPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.apply {
            setNavigationOnClickListener { presenter.onBackPressed() }
        }

        connectButton.setOnClickListener { connectSession() }

    }

    private fun connectSession() {
        val url = urlValue.text.toString()
        val realm = realmValue.text.toString()

        if (realm.isEmpty()) {
            showMessage(getString(R.string.empty_realm))
            return
        }

        if (url.isEmpty()) {
            showMessage(getString(R.string.empty_server_url))
            return
        }

        presenter.connectSession(url, realm)
    }


    override fun showProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}