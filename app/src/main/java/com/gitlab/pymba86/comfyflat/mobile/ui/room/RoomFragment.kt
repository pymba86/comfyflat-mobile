package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_room.*

class RoomFragment : BaseFragment(), RoomView {

    override val layoutRes: Int = R.layout.fragment_room

    @InjectPresenter
    lateinit var presenter: RoomPresenter

    @ProvidePresenter
    fun providePresenter(): RoomPresenter =
        scope.getInstance(RoomPresenter::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }


    override fun showDevices(show: Boolean, projects: List<Device>) {

    }

}