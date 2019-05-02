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

    @InjectPresenter
    lateinit var presenter: RoomPresenter

    private val deviceAdapter by lazy { DeviceAdapter(mvpDelegate) }

    @ProvidePresenter
    fun providePresenter(): RoomPresenter =
        scope.getInstance(RoomPresenter::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = deviceAdapter
        }

        swipeToRefresh.setOnRefreshListener { presenter.refreshDevices() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
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
    }

    override fun showEmptyView(show: Boolean) {

    }

    override fun showEmptyError(show: Boolean, message: String?) {

    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }


    override fun showDevices(show: Boolean, devices: List<Device>) {
        deviceAdapter.data.apply {
            clear()
            add(devices[0].category)
            add(devices[0])
            for (i in 1 until devices.size) {
                if (devices[i].category.id != devices[i - 1].category.id) {
                    add(devices[i].category)
                    add(devices[i])
                } else {
                    add(devices[i])
                }
            }
        }
        deviceAdapter.notifyDataSetChanged()
    }

}