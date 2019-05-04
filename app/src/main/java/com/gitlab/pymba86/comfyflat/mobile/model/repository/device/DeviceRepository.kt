package com.gitlab.pymba86.comfyflat.mobile.model.repository.device

import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.model.system.SchedulersProvider
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.Wamp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import hu.akarnokd.rxjava.interop.RxJavaInterop
import java.lang.reflect.Type
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val wampClient: Wamp,
    private val moshi: Moshi,
    private val schedulers: SchedulersProvider
) {

    fun stateWampClient() = wampClient.state

    fun getRoomDevices(roomId: Int) = RxJavaInterop
        .toV2Observable(wampClient.client.call("get_devices_room", String, roomId))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .map {
            val listDeviceType: Type = Types.newParameterizedType(List::class.java, Device::class.java)
            val adapter: JsonAdapter<List<Device>> = moshi.adapter(listDeviceType)
            adapter.failOnUnknown().serializeNulls().nonNull().fromJson(it.arguments().toString()).orEmpty()
        }
        .singleOrError()
}