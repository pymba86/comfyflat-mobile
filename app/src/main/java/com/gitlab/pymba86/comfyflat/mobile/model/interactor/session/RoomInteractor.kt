package com.gitlab.pymba86.comfyflat.mobile.model.interactor.session


import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import javax.inject.Inject

class RoomInteractor @Inject constructor(
) {

    private val listRooms = listOf(
        Room(1, "Детская", 4,3),
        Room(1, "Кухня", 2,1),
        Room(1, "Спальня", 3,2)
    )

    fun getRooms(page: Int) = listRooms
}