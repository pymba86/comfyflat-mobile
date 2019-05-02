package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCategory
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.gitlab.pymba86.comfyflat.mobile.ui.room.holder.DeviceViewHolder
import com.gitlab.pymba86.comfyflat.mobile.ui.room.holder.HeaderViewHolder

class DeviceAdapter(
    private val parentDelegate: MvpDelegate<*>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CATEGORY = 0
        private const val TYPE_DEVICE = 1
    }

    var data = arrayListOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DEVICE -> DeviceViewHolder(parent.inflate(R.layout.item_device), parentDelegate)
            TYPE_CATEGORY -> HeaderViewHolder(parent.inflate(R.layout.item_category))
            else -> throw RuntimeException()
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DeviceCategory -> TYPE_CATEGORY
            else -> TYPE_DEVICE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_CATEGORY -> (holder as HeaderViewHolder).bind(data[position] as DeviceCategory)
            TYPE_DEVICE -> (holder as DeviceViewHolder).bind(data[position] as Device)
        }
    }
}