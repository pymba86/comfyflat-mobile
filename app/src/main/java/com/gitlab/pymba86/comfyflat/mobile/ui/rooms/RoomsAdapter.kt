package com.gitlab.pymba86.comfyflat.mobile.ui.rooms

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.ProgressAdapterDelegate
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.ProgressItem
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.RoomAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class RoomsAdapter(
    private val nextPageListener: () -> Unit,
    private val onClickListener: (Long) -> Unit
)  : ListDelegationAdapter<MutableList<Any>>() {

    init {
        items = mutableListOf()
        delegatesManager.addDelegate(RoomAdapterDelegate { onClickListener(it.id) })
        delegatesManager.addDelegate(ProgressAdapterDelegate())
    }

    fun setData(projects: List<Room>) {
        val progress = isProgress()
        val oldItems = items.toList()

        items.clear()
        items.addAll(projects)
        if (progress) items.add(ProgressItem())

        DiffUtil
            .calculateDiff(DiffCallback(items, oldItems), false)
            .dispatchUpdatesTo(this)
    }

    fun showProgress(isVisible: Boolean) {
        val progress = isProgress()

        if (isVisible && !progress) {
            items.add(ProgressItem())
            notifyItemInserted(items.lastIndex)
        } else if (!isVisible && progress) {
            items.remove(items.last())
            notifyItemRemoved(items.size)
        }
    }

    private fun isProgress() = items.isNotEmpty() && items.last() is ProgressItem

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (position == items.size - 10) nextPageListener()
    }

    private class DiffCallback(
        private val newItems: List<Any>,
        private val oldItems: List<Any>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is Room && oldItem is Room) {
                newItem.id == oldItem.id
            } else {
                newItem is ProgressItem && oldItem is ProgressItem
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is Room && oldItem is Room) {
                newItem == oldItem
            } else {
                true
            }
        }
    }

}