package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomPresenter
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.ProgressAdapterDelegate
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.ProgressItem
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class RoomDeviceAdapter(
    private val presenter: RoomPresenter,
    private val nextPageListener: () -> Unit
) : ListDelegationAdapter<MutableList<Any>>() {

    init {
        items = mutableListOf()
        delegatesManager.addDelegate(RoomDeviceAdapterDelegate(presenter))
            .addDelegate(ProgressAdapterDelegate())
    }

    fun setData(data: List<Device>) {
        val oldItems = items.toList()

        items.clear()
        items.addAll(data)

        DiffUtil
            .calculateDiff(DiffCallback(items, oldItems), false)
            .dispatchUpdatesTo(this)
    }

    fun showProgress(isVisible: Boolean) {
        val oldData = items.toList()
        val currentProgress = isProgress()

        if (isVisible && !currentProgress) {
            items.add(ProgressItem())
            notifyItemInserted(items.lastIndex)
        } else if (!isVisible && currentProgress) {
            items.remove(items.last())
            notifyItemRemoved(oldData.lastIndex)
        }
    }

    private fun isProgress() = items.isNotEmpty() && items.last() is ProgressItem

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        if (position == items.size - NEXT_PAGE_OFFSET) nextPageListener()
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

            return if (newItem is Device && oldItem is Device) {
                newItem.id == oldItem.id
            } else {
                newItem is ProgressItem && oldItem is ProgressItem
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is Device && oldItem is Device) {
                newItem == oldItem
            } else {
                true
            }
        }
    }

    companion object {
        private const val NEXT_PAGE_OFFSET = 10
    }

}