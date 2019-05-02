package com.gitlab.pymba86.comfyflat.mobile.entity.device

import com.google.gson.annotations.SerializedName

data class DeviceFunction(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: Int,
    @SerializedName("measure") val measure: String,
    @SerializedName("isWriteAccess") val isWriteAccess: Boolean,
    @SerializedName("minValue") val minValue: Int,
    @SerializedName("maxValue") val maxValue: Int
)