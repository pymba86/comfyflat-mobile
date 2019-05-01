package com.gitlab.pymba86.comfyflat.mobile.entity.device

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: DeviceCategory,
    @SerializedName("functions") val functions: List<DeviceFunction>
)