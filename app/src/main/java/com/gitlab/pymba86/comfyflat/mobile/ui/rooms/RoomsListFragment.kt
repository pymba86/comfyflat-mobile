package com.gitlab.pymba86.comfyflat.mobile.ui.rooms

import android.os.Bundle
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment

class RoomsListFragment : BaseFragment() {


    override val layoutRes = R.layout.fragment_rooms

    companion object {
        private const val ARG_MODE = "plf_mode"

        fun create(mode: Int) = RoomsListFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_MODE, mode)
            }
        }
    }
}