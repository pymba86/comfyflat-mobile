package com.gitlab.pymba86.comfyflat.mobile.entity.app.session

data class UserAccount(
    val userId: Long,
    val userName: String,
    val session: Session
)