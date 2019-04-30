package com.gitlab.pymba86.comfyflat.mobile.ui.user.info

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.User
import com.gitlab.pymba86.comfyflat.mobile.extension.showSnackMessage
import com.gitlab.pymba86.comfyflat.mobile.extension.showTextOrHide
import com.gitlab.pymba86.comfyflat.mobile.presentation.user.info.UserInfoPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.user.info.UserInfoView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment : BaseFragment(), UserInfoView {

    override val layoutRes = R.layout.fragment_user_info
    private var user: User? = null

    @InjectPresenter
    lateinit var presenter: UserInfoPresenter

    @ProvidePresenter
    fun providePresenter(): UserInfoPresenter =
        scope.getInstance(UserInfoPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun showUser(user: User) {
        this.user = user
        toolbar.title = user.username
        avatarImageView.text = user.username.getOrElse(0){'A'}.toUpperCase().toString()
        usernameTextView.text = user.username
        userIdTextView.text = user.managerKey

        platformUrlTextView.showTextOrHide(user.platformUrl)
    }

    override fun showProgress(show: Boolean) {
        showProgressDialog(show)
    }

    override fun showMessage(msg: String) {
        showSnackMessage(msg)
    }
}