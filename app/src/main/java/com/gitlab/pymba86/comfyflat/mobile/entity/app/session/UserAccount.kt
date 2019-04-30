package com.gitlab.pymba86.comfyflat.mobile.entity.app.session

data class UserAccount(
    val userId: Long,
    val managerKey: String,
    val userName: String
) {
    val id: String = "$userId : $managerKey"
}