package com.gitlab.pymba86.comfyflat.mobile.entity.device

data class Device(
    val id: Int,
    val name: String,
    val category: DeviceCategory,
    val functions: List<DeviceFunction>
)