package com.gitlab.pymba86.comfyflat.mobile.model.interactor.session

import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.UserAccount
import javax.inject.Inject

class SessionInteractor @Inject constructor(
) {

    private var userAccount: UserAccount? =   UserAccount(1, "babyish", "pymba86")

    private val listUser = listOf(
        UserAccount(1, "babyish", "pymba86"),
        UserAccount(2, "babyish", "root"),
        UserAccount(3, "babyish", "judchin")
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