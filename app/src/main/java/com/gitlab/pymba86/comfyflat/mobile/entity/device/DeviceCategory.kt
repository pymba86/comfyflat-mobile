package com.gitlab.pymba86.comfyflat.mobile.entity.device

import com.google.gson.annotations.SerializedName

data class DeviceCategory(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)