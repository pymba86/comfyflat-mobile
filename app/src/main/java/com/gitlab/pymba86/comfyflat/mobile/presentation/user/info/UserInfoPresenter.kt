package com.gitlab.pymba86.comfyflat.mobile.presentation.user.info

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.user.UserInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.UserId
import javax.inject.Inject

@InjectViewState
class UserInfoPresenter @Inject constructor(
    private val userInteractor: UserInteractor,
    private val router: FlowRouter,
    @UserId userIdWrapper: PrimitiveWrapper<Long>
) : BasePresenter<UserInfoView>() {
    private val userId = userIdWrapper.value

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        userInteractor
            .getUser(userId)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribe(
                { viewState.showUser(it) },
                {  }
            )
            .connect()
    }

    fun onBackPressed() = router.exit()
}