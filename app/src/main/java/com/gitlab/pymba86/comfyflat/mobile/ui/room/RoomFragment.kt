package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.device.Device
import com.gitlab.pymba86.comfyflat.mobile.extension.showSnackMessage
import com.gitlab.pymba86.comfyflat.mobile.extension.visible
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.room.RoomView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.layout_base_list.*

class RoomFragment : BaseFragment(), RoomView {

    override val layoutRes: Int = R.layout.fragment_room

    private val adapter by lazy { RoomDeviceAdapter { } }

    @InjectPresenter
    lateinit var presenter: RoomPresenter

    @ProvidePresenter
    fun providePresenter(): RoomPresenter =
        scope.getInstance(RoomPresenter::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@RoomFragment.adapter
        }


        toolbar.setNavigationOnClickListener { onBackPressed() }
        swipeToRefresh.setOnRefreshListener { presenter.refreshRoomDevices() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }


    override fun showDevices(show: Boolean, devices: List<Device>) {
        recyclerView.visible(show)
        postViewAction { adapter.setData(devices) }
    }

    override fun showRefreshProgress(show: Boolean) {
        postViewAction { swipeToRefresh.isRefreshing = show }
    }

    override fun showEmptyProgress(show: Boolean) {
        fullscreenProgressView.visible(show)

        //trick for disable and hide swipeToRefresh on fullscreen progress
        swipeToRefresh.visible(!show)
        postViewAction { swipeToRefresh.isRefreshing = false }
    }

    override fun showPageProgress(show: Boolean) {
        postViewAction { adapter.showProgress(show) }
    }

    override fun showEmptyView(show: Boolean) {

    }

    override fun showEmptyError(show: Boolean, message: String?) {

    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}