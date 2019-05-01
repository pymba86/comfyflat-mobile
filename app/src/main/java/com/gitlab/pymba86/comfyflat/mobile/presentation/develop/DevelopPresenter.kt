package com.gitlab.pymba86.comfyflat.mobile.presentation.develop

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.app.AppInfoInteractor
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class DevelopPresenter @Inject constructor(
    private val router: Router,
    private val appInfoInteractor: AppInfoInteractor
) : BasePresenter<DevelopView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        appInfoInteractor
            .getAppDevelopers()
            .subscribe(
                { viewState.showDevelopers(it) },
                { Timber.e("getAppLibraries error: $it") }
            )
            .connect()
    }

    fun onBackPressed() = router.exit()
}