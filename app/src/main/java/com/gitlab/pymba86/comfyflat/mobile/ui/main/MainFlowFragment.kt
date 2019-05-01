package com.gitlab.pymba86.comfyflat.mobile.ui.main

import android.os.Bundle
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import toothpick.Toothpick
import javax.inject.Inject

class MainFlowFragment : BaseFragment() {

    @Inject
    lateinit var menuController: GlobalMenuController

    override val layoutRes = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener { menuController.open() }
    }
}