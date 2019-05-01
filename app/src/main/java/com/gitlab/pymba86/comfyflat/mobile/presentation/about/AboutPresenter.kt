package com.gitlab.pymba86.comfyflat.mobile.presentation.about

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.app.AppInfoInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class AboutPresenter @Inject constructor(
    private val router: FlowRouter,
    private val menuController: GlobalMenuController,
    private val appInfoInteractor: AppInfoInteractor
) : BasePresenter<AboutView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        appInfoInteractor
            .getAppInfo()
            .subscribe(
                { viewState.showAppInfo(it) },
                { Timber.e(it) }
            )
            .connect()
    }


    fun onDevelopersClicked() = router.startFlow(Screens.Develop)

    fun onMenuPressed() = menuController.open()
    fun onBackPressed() = router.exit()
}