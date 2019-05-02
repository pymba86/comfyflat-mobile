package com.gitlab.pymba86.comfyflat.mobile.ui.room.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.MvpDelegate

abstract class MvpViewHolder(private val mParentDelegate: MvpDelegate<*>, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private var mMvpDelegate: MvpDelegate<*>? = null

    private val mvpDelegate: MvpDelegate<*>?
        get() {
            if (mvpChildId == null) {
                return null
            }
            if (mMvpDelegate == null) {
                mMvpDelegate = MvpDelegate<MvpViewHolder>(this)
                mMvpDelegate!!.setParentDelegate(mParentDelegate, mvpChildId)
            }
            return mMvpDelegate
        }

    protected abstract val mvpChildId: String?

    protected fun destroyMvpDelegate() {
        if (mvpDelegate != null) {
            mvpDelegate!!.onSaveInstanceState()
            mvpDelegate!!.onDetach()
            mMvpDelegate = null
        }
    }

    protected fun createMvpDelegate() {
        if (mvpDelegate != null) {
            mvpDelegate!!.onCreate()
            mvpDelegate!!.onAttach()
        }
    }
}