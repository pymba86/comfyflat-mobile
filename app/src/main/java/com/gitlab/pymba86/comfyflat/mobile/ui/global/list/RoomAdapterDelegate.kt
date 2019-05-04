package com.gitlab.pymba86.comfyflat.mobile.ui.global.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.extension.getTintDrawable
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.gitlab.pymba86.comfyflat.mobile.extension.setStartDrawable
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapterDelegate(private val clickListener: (Room) -> Unit) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is Room

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_room))
    }

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) =
        (viewHolder as ViewHolder).bind(items[position] as Room)

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var room: Room

        init {
            view.setOnClickListener { clickListener(room) }
        }

        fun bind(room: Room) {
            this.room = room
            with(itemView) {
                titleTextView.text = room.name
                avatarImageView.text = room.name.getOrElse(0){'A'}.toUpperCase().toString()
            }
        }
    }
}