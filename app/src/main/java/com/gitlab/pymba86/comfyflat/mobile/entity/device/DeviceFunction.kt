package com.gitlab.pymba86.comfyflat.mobile.entity.device

import com.google.gson.annotations.SerializedName

data class DeviceFunction(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: Long,
    @SerializedName("measure") val measure: String,
    @SerializedName("isWriteAccess") val isWriteAccess: Boolean,
    @SerializedName("minValue") val minValue: Long,
    @SerializedName("maxValue") val maxValue: Long
)