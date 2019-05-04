package com.gitlab.pymba86.comfyflat.mobile.model.repository.room


import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.Wamp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.akarnokd.rxjava.interop.RxJavaInterop
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val wampClient: Wamp,
    private val gson: Gson,
    private val schedulers: SchedulersProvider
) {

    fun stateWampClient() = wampClient.state

    fun getRooms() = RxJavaInterop
        .toV2Observable(wampClient.client.call("get_rooms", String, null))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .map {
            gson.fromJson<List<Room>>(it.arguments().toString(), object : TypeToken<List<Room>>() {}.type)
        }
        .singleOrError()
}