package com.gitlab.pymba86.comfyflat.mobile.ui.global.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppDeveloper
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_app_developer.view.*

class AppDevelopAdapterDelegate(private val clickListener: (AppDeveloper) -> Unit) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is AppDeveloper

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_app_developer))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) =
        (viewHolder as ViewHolder).bind(items[position] as AppDeveloper)

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var appDeveloper: AppDeveloper

        init {
            view.setOnClickListener { clickListener(appDeveloper) }
        }

        fun bind(appDeveloper: AppDeveloper) {
            this.appDeveloper = appDeveloper
            with(itemView) {
                titleTextView.text = appDeveloper.userName
            }
        }
    }
}