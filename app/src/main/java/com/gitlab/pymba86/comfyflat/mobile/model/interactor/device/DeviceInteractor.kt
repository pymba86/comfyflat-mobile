package com.gitlab.pymba86.comfyflat.mobile.model.interactor.device

import com.gitlab.pymba86.comfyflat.mobile.model.repository.device.DeviceRepository
import javax.inject.Inject

class DeviceInteractor @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    fun stateWampClient() = deviceRepository.stateWampClient()

    fun getRoomDevices(roomId: Int, page: Int) = deviceRepository.getRoomDevices(roomId)
}