package com.gitlab.pymba86.comfyflat.mobile.model.repository.room


import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.Wamp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import hu.akarnokd.rxjava.interop.RxJavaInterop
import java.lang.reflect.Type
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val wampClient: Wamp,
    private val moshi: Moshi,
    private val schedulers: SchedulersProvider
) {

    fun stateWampClient() = wampClient.state

    fun getRooms() = RxJavaInterop
        .toV2Observable(wampClient.client.call("get_rooms", String, null))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .map {
            val listRoomType: Type = Types.newParameterizedType(List::class.java, Room::class.java)
            val adapter: JsonAdapter<List<Room>> = moshi.adapter(listRoomType)
            adapter.failOnUnknown().serializeNulls().nonNull().fromJson(it.arguments().toString()).orEmpty()
        }
        .singleOrError()
}