package com.gitlab.pymba86.comfyflat.mobile.model.repository.device

import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCallFunction
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

    fun stateFunctionChange() = RxJavaInterop
        .toV2Observable(wampClient.client.makeSubscription("sub_devices_function", String::class.java))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .map {
            val adapter: JsonAdapter<DeviceCallFunction> = moshi.adapter(DeviceCallFunction::class.java)
            adapter.failOnUnknown().serializeNulls().nonNull().fromJson(it)!!
        }

    fun setParamDevice(idDevice: Int, idFunction: Int, value: Int) = RxJavaInterop
        .toV2Observable(wampClient.client.call(
            "set_param_device", String::class.java,
            idDevice, idFunction, value))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .map {
            val adapter: JsonAdapter<Boolean> = moshi.adapter(Boolean::class.java)
            adapter.failOnUnknown().serializeNulls().nonNull().fromJson(it)!!
        }
        .singleOrError()

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