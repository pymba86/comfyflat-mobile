package com.gitlab.pymba86.comfyflat.mobile.model.interactor.session

import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.Session
import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.UserAccount
import javax.inject.Inject

class SessionInteractor @Inject constructor(
) {

    private var userAccount: UserAccount? =   UserAccount(1, "pymba86", Session("","babyish"))

    private val listUser = listOf(
        UserAccount(1, "pymba86",  Session("","babyish")),
        UserAccount(2, "root", Session("","babyish")),
        UserAccount(3, "judchin", Session("","babyish"))
    )
    fun getUserAccounts() = listUser
    fun getCurrentUserAccount() = userAccount

    fun setCurrentUserAccount(accountId: Long): UserAccount? {
        this.userAccount = listUser[accountId.toInt()]
        return userAccount
    }




    companion object {
        private const val PARAMETER_CODE = "code"
    }
}