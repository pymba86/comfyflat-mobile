package com.gitlab.pymba86.comfyflat.mobile.presentation.session.info

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.session.SessionInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import javax.inject.Inject

@InjectViewState
class SessionInfoPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    private val router: FlowRouter
) : BasePresenter<SessionInfoView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        sessionInteractor.getCurrentSession()?.let { viewState.showSession(it) }
    }

    fun onBackPressed() = router.exit()
}