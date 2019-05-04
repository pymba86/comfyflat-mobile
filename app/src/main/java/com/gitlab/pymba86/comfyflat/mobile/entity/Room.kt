package com.gitlab.pymba86.comfyflat.mobile.entity

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)