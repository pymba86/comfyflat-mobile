package com.gitlab.pymba86.comfyflat.mobile.ui.room.holder


import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.DeviceCategory

class HeaderViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.nameTextView)

    fun bind(category: DeviceCategory) {
        name.text = category.name
    }
}