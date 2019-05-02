package com.gitlab.pymba86.comfyflat.mobile.ui.room.holder

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.arellomobile.mvp.MvpDelegate
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCallFunction
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceFunction
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.gitlab.pymba86.comfyflat.mobile.presentation.device.DeviceView
import kotlin.collections.set

class DeviceViewHolder(
    private val view: View,
    private val parentDelegate: MvpDelegate<*>
) : MvpViewHolder(parentDelegate, view), DeviceView {

    private var device: Device? = null
    private val params = mutableMapOf<Int, ParamHolder>()

    override val mvpChildId: String? = if (device == null) null else device?.id.toString()


    private val name = view.findViewById<TextView>(R.id.nameTextView)
    private val functionContainer = view.findViewById<LinearLayout>(R.id.functionContainerViewGroup)


    fun bind(device: Device) {
        destroyMvpDelegate()
        this.device = device
        createMvpDelegate()
        functionContainer.removeAllViews()
        name.text = device.name
        if (device.functions.isEmpty()) view.alpha = 0.3f
        device.functions.forEach {
            params[it.id] = when {
                !it.isWriteAccess -> InfoHolder(functionContainer, it)
                it.minValue == 0 && it.maxValue == 1 -> SwitchHolder(functionContainer, it)
                else -> SliderHolder(functionContainer, it)
            }
        }
    }

    override fun setData(funcLite: DeviceCallFunction) {
        device?.functions?.find { it.id == funcLite.idFunction }?.apply {
           val value = funcLite.value
            params[id]?.setValue(value)
        }
    }

    private abstract class ParamHolder {
        abstract fun setValue(value: Any)
    }

    private inner class SwitchHolder(parent: ViewGroup, function: DeviceFunction) : ParamHolder() {

        private val view = parent.inflate(R.layout.layout_switch_function, true)
        private var switch: Switch

        init {
            switch = view.findViewById(R.id.trigger)
            switch.text = function.name
            switch.isChecked = function.value != 0
            switch.setOnCheckedChangeListener { _, isChecked ->
                val value = if (isChecked) 1 else 0
                //functionListener(CallFunction(device.id, function.id, value))
            }
        }

        override fun setValue(value: Any) {
            switch.isChecked = value as Int == 1
        }
    }

    private inner class InfoHolder(parent: ViewGroup, private val function: DeviceFunction) : ParamHolder() {

        private val view = parent.inflate(R.layout.layout_info_function, true)
        private var nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private var valueTextView: TextView = view.findViewById(R.id.valueTextView)

        init {
            nameTextView.text = function.name
            valueTextView.text = "${function.value}, ${function.measure}"
        }

        override fun setValue(value: Any) {
            valueTextView.text = "$value, ${function.measure}"
        }
    }

    private inner class SliderHolder(parent: ViewGroup, function: DeviceFunction) : ParamHolder() {

        private val view = parent.inflate(R.layout.layout_slider_function, true)
        private var seekBar: DiscreteSeekBar
        private var valueTextView: TextView

        init {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            nameTextView.text = "${function.name}, ${function.measure}"
            seekBar = view.findViewById(R.id.seekBar)
            seekBar.min = function.minValue
            seekBar.max = function.maxValue
            seekBar.progress = function.value
            val minTextView: TextView = view.findViewById(R.id.minValueTextView)
            minTextView.text = function.minValue.toString()
            val maxTextView: TextView = view.findViewById(R.id.maxValueTextView)
            maxTextView.text = function.maxValue.toString()
            valueTextView = view.findViewById(R.id.valueTextView)
            valueTextView.text = function.value.toString()
            seekBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
                var isTrackingTouch = false
                var startValue = 0
                override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {
                    if (startValue == 0 && fromUser) {
                        valueTextView.text = seekBar.progress.toString()

                    }
                }

                override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {
                    startValue = seekBar.progress
                }

                override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {
                    if (startValue != seekBar.progress) {
                        valueTextView.text = seekBar.progress.toString()

                    }
                    startValue = 0
                }
            })
        }

        override fun setValue(value: Any) {
            seekBar.progress = value as Int
            valueTextView.text = value.toString()
        }
    }

}