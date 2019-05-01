package com.gitlab.pymba86.comfyflat.mobile.entity

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("count_devices") val countDevices: Long,
    @SerializedName("count_type_devices") val countTypeDevices: Long
)