package com.gitlab.pymba86.comfyflat.mobile.model.interactor.user

import com.gitlab.pymba86.comfyflat.mobile.entity.User
import io.reactivex.Observable
import javax.inject.Inject

class UserInteractor @Inject constructor(
) {

    fun getUser(id: Long) =
        Observable.fromCallable {User(1,"pymba86","babyish","ws://example.com:55555")}

}