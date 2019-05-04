package com.gitlab.pymba86.comfyflat.mobile.entity.device

data class DeviceFunction(
    val id: Int,
    val name: String,
    val value: Float,
    val measure: String,
    val isWriteAccess: Boolean,
    val minValue: Int,
    val maxValue: Int
)