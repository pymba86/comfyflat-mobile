package com.gitlab.pymba86.comfyflat.mobile.ui.about

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppInfo
import com.gitlab.pymba86.comfyflat.mobile.presentation.about.AboutPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.about.AboutView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment(), AboutView {
    override val layoutRes = R.layout.fragment_about

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    @ProvidePresenter
    fun providePresenter(): AboutPresenter =
        scope.getInstance(AboutPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener { presenter.onMenuPressed() }
        authorsView.setOnClickListener { presenter.onDevelopersClicked() }
    }

    override fun showAppInfo(appInfo: AppInfo) {
        versionTextView.text = "${appInfo.versionName}"
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}