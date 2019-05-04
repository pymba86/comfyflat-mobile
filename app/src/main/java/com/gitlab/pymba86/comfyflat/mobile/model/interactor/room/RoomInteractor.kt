package com.gitlab.pymba86.comfyflat.mobile.model.interactor.room


import com.gitlab.pymba86.comfyflat.mobile.model.repository.room.RoomRepository
import javax.inject.Inject

class RoomInteractor @Inject constructor(
    private val roomRepository: RoomRepository
) {

    fun stateWampClient() = roomRepository.stateWampClient()

    fun getRooms(page: Int) = roomRepository.getRooms()
}