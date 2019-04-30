package com.gitlab.pymba86.comfyflat.mobile.entity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String,
    @SerializedName("manager_key") val managerKey: String,
    @SerializedName("platform_url") val platformUrl: String
)
