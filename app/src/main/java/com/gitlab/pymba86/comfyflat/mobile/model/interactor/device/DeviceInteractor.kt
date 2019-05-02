package com.gitlab.pymba86.comfyflat.mobile.model.interactor.device


import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCategory
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceFunction
import javax.inject.Inject

class DeviceInteractor @Inject constructor(
) {

    private val listDevices = listOf(
        Device(
            1, "Устройство 1", DeviceCategory(1, "Отопление"),
            listOf(
                DeviceFunction(1, "Функция 1", 20, "Измерение 1", true, 0, 100)
            )
        )
    )

    fun getDevices(roomId: Long) = listDevices
}