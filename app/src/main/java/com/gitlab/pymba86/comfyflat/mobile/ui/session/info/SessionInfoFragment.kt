package com.gitlab.pymba86.comfyflat.mobile.ui.session.info

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.extension.showSnackMessage
import com.gitlab.pymba86.comfyflat.mobile.extension.showTextOrHide
import com.gitlab.pymba86.comfyflat.mobile.presentation.session.info.SessionInfoView
import com.gitlab.pymba86.comfyflat.mobile.presentation.session.info.SessionInfoPresenter
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_session_info.*

class SessionInfoFragment : BaseFragment(), SessionInfoView {

    override val layoutRes = R.layout.fragment_session_info
    private var session: Session? = null

    @InjectPresenter
    lateinit var presenter: SessionInfoPresenter

    @ProvidePresenter
    fun providePresenter(): SessionInfoPresenter =
        scope.getInstance(SessionInfoPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun showSession(session: Session) {
        this.session = session
        toolbar.title = session.realm
        avatarImageView.text = session.realm.getOrElse(0){'A'}.toUpperCase().toString()
        realmTextView.text = session.realm

        urlTextView.showTextOrHide(session.url)
    }

    override fun showProgress(show: Boolean) {
        showProgressDialog(show)
    }

    override fun showMessage(msg: String) {
        showSnackMessage(msg)
    }
}