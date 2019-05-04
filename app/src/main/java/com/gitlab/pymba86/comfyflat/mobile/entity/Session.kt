package com.gitlab.pymba86.comfyflat.mobile.entity


data class Session(
    val url: String,
    val realm: String
) {
    val id: String = "$url : $realm"
}