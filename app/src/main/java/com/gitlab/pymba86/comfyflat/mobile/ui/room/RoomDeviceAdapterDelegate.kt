package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCallFunction
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceFunction
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_device.view.*

class RoomDeviceAdapterDelegate : AdapterDelegate<MutableList<Any>>() {


    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DeviceViewHolder(parent.inflate(R.layout.item_device))
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is Device
    }

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as DeviceViewHolder).bind(items[position] as Device)
    }

    private class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private interface ParamHolder {
            fun setValue(value: Any)
        }

        private var currentDevice: Device? = null
        private val currentParams = mutableMapOf<Int, ParamHolder>()

        fun bind(device: Device) = with(itemView) {
            currentDevice = device
            functionContainerViewGroup.removeAllViews()
            nameTextView.text = device.name
            categoryTextView.text = device.category.name

            if (device.functions.isEmpty()) itemView.alpha = 0.3f

            device.functions.forEach {
                currentParams[it.id] = when {
                    !it.isWriteAccess -> InfoHolder(functionContainerViewGroup, it)
                    (it.minValue == 0 && it.maxValue == 1) -> SwitchHolder(functionContainerViewGroup, it)
                    else -> SliderHolder(functionContainerViewGroup, it)
                }
            }
        }

        private fun setData(funcLite: DeviceCallFunction) {
            currentDevice?.functions?.find { it.id == funcLite.idFunction }?.apply {
                currentParams[id]?.setValue(funcLite.value)
            }
        }

        private inner class SwitchHolder(parent: ViewGroup, function: DeviceFunction) : ParamHolder {

            private val view = parent.inflate(R.layout.layout_switch_function, true)
            private var switch: Switch

            init {
                switch = view.findViewById(R.id.trigger)
                switch.text = function.name
                switch.isChecked = function.value != 0
                switch.setOnCheckedChangeListener { _, isChecked ->
                    val value = if (isChecked) 1 else 0
                }
            }

            override fun setValue(value: Any) {
                switch.isChecked = value as Int == 1
            }
        }

        private inner class InfoHolder(parent: ViewGroup, private val function: DeviceFunction) : ParamHolder {

            private val view = parent.inflate(R.layout.layout_info_function, true)
            private var nameTextView: TextView = view.findViewById(R.id.nameInfoTextView)
            private var valueTextView: TextView = view.findViewById(R.id.valueInfoTextView)

            init {
                nameTextView.text = function.name
                valueTextView.text = "${function.value}, ${function.measure}"
            }

            override fun setValue(value: Any) {
                valueTextView.text = "$value, ${function.measure}"
            }
        }

        private inner class SliderHolder(parent: ViewGroup, function: DeviceFunction) : ParamHolder {

            private val view = parent.inflate(R.layout.layout_slider_function, true)
            private var seekBar: SeekBar
            private var valueTextView: TextView

            init {
                val nameTextView: TextView = view.findViewById(R.id.nameSliderTextView)
                nameTextView.text = "${function.name}, ${function.measure}"
                seekBar = view.findViewById(R.id.seekBar)
                seekBar.max = function.maxValue
                seekBar.progress = function.value
                val minTextView: TextView = view.findViewById(R.id.minValueSliderTextView)
                minTextView.text = function.minValue.toString()
                val maxTextView: TextView = view.findViewById(R.id.maxValueSliderTextView)
                maxTextView.text = function.maxValue.toString()
                valueTextView = view.findViewById(R.id.valueSliderTextView)
                valueTextView.text = function.value.toString()
            }

            override fun setValue(value: Any) {
                seekBar.progress = value as Int
                valueTextView.text = value.toString()
            }
        }

    }

}