package com.gitlab.pymba86.comfyflat.mobile.ui.auth

import android.os.Build
import android.os.Bundle
import android.webkit.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.presentation.auth.AuthPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.auth.AuthView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment(), AuthView,  CustomServerAuthFragment.OnClickListener {

    override val layoutRes = R.layout.fragment_auth

    override val customLogin = { url: String, token: String -> presenter.loginOnCustomServer(url, token) }

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun providePresenter(): AuthPresenter =
        scope.getInstance(AuthPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.apply {
            setNavigationOnClickListener { presenter.onBackPressed() }
            inflateMenu(R.menu.custom_auth_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.customAuthAction -> CustomServerAuthFragment().show(childFragmentManager, null)
                }
                true
            }
        }

    }

}