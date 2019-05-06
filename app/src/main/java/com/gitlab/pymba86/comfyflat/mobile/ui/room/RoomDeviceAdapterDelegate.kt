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
import com.gitlab.pymba86.comfyflat.mobile.presentation.device.DeviceView
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomPresenter
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.item_device.view.*

class RoomDeviceAdapterDelegate constructor(
    private val presenter: RoomPresenter
) : AdapterDelegate<MutableList<Any>>() {


    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DeviceViewHolder(parent.inflate(R.layout.item_device), presenter)
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

    class DeviceViewHolder(view: View, private val presenter: RoomPresenter) : RecyclerView.ViewHolder(view),
        DeviceView {

        private interface ParamHolder {
            fun setValue(value: Any)
        }

        private var currentDevice: Device? = null
        private val currentParams = mutableMapOf<Int, ParamHolder>()


        fun bind(device: Device) {

            with(itemView) {
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
            presenter.subscribeDeviceView(device.id, this)
        }

        override fun setData(funcLite: DeviceCallFunction) {
            currentDevice?.functions?.find { it.id == funcLite.idFunction }?.apply {
                currentParams[id]?.setValue(funcLite.value)
            }
        }

        private inner class SwitchHolder(parent: ViewGroup, function: DeviceFunction) : ParamHolder {

            private val view = parent.inflate(R.layout.layout_switch_function, true)
            private var ignoreCheckedChange = false
            private var switch: Switch

            init {
                switch = view.findViewById(R.id.trigger)
                switch.text = function.name
                switch.isChecked = function.value.equals(0)
                switch.setOnCheckedChangeListener { _, isChecked ->

                    if (!ignoreCheckedChange) {
                        val value = if (isChecked) 1 else 0
                        presenter.publishDataDevice(DeviceCallFunction(currentDevice?.id!!, function.id, value))
                    }
                }
            }

            override fun setValue(value: Any) {
                ignoreCheckedChange = true
                switch.isChecked = value as Int == 1
                ignoreCheckedChange = false
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
            private var ignoreCheckedChange = false
            private var seekBar: BubbleSeekBar
            private var valueTextView: TextView

            init {
                val nameTextView: TextView = view.findViewById(R.id.nameSliderTextView)
                nameTextView.text = "${function.name}, ${function.measure}"
                seekBar = view.findViewById(R.id.seekBar)

                seekBar.configBuilder
                    .min(function.minValue.toFloat())
                    .max(function.maxValue.toFloat())
                    .progress(function.value.toInt().toFloat())
                    .build()


                val minTextView: TextView = view.findViewById(R.id.minValueSliderTextView)
                minTextView.text = function.minValue.toString()
                val maxTextView: TextView = view.findViewById(R.id.maxValueSliderTextView)
                maxTextView.text = function.maxValue.toString()
                valueTextView = view.findViewById(R.id.valueSliderTextView)
                valueTextView.text = function.value.toString()

                seekBar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {

                    var startValue = 0
                    var oldProgress = 0
                    var isOn = true

                    override fun onProgressChanged(
                        bubbleSeekBar: BubbleSeekBar?,
                        progress: Int,
                        progressFloat: Float,
                        fromUser: Boolean
                    ) {

                        if (!ignoreCheckedChange) {
                            valueTextView.text = seekBar.progress.toString()
                            presenter.publishDataDevice(DeviceCallFunction(currentDevice?.id!!, function.id, progress))
                        }

                    }

                    override fun getProgressOnActionUp(
                        bubbleSeekBar: BubbleSeekBar?,
                        progress: Int,
                        progressFloat: Float
                    ) {
                        oldProgress = seekBar.progress
                    }

                    override fun getProgressOnFinally(
                        bubbleSeekBar: BubbleSeekBar?,
                        progress: Int,
                        progressFloat: Float,
                        fromUser: Boolean
                    ) {

                        startValue = seekBar.progress
                    }
                }
            }

            override fun setValue(value: Any) {
                ignoreCheckedChange = true
                val newValue: Int = value as Int
                seekBar.setProgress(newValue.toFloat())
                valueTextView.text = value.toString()
                ignoreCheckedChange = false
            }
        }

    }

}