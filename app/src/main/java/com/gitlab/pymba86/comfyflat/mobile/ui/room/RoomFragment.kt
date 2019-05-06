package com.gitlab.pymba86.comfyflat.mobile.ui.room

import android.graphics.Color
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
import com.gitlab.pymba86.comfyflat.mobile.ui.global.ZeroViewHolder
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.layout_base_list.*
import kotlinx.android.synthetic.main.layout_zero.*
import toothpick.Toothpick

class RoomFragment : BaseFragment(), RoomView {

    override val layoutRes: Int = R.layout.fragment_room

    private val adapter by lazy { RoomDeviceAdapter( presenter) {  presenter.loadNextLabelsPage()} }

    @InjectPresenter
    lateinit var presenter: RoomPresenter

    private var zeroViewHolder: ZeroViewHolder? = null

    @ProvidePresenter
    fun providePresenter(): RoomPresenter =
        scope.getInstance(RoomPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        toolbar.setNavigationOnClickListener { onBackPressed() }
        swipeToRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeToRefresh.setOnRefreshListener { presenter.refreshRoomDevices() }
        zeroViewHolder = ZeroViewHolder(zeroLayout) { presenter.refreshRoomDevices() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }


    override fun showDevices(show: Boolean, devices: List<Device>) {
        zeroViewHolder?.hide()
        recyclerView.visible(show)
        postViewAction { adapter.setData(devices) }
    }

    override fun showRefreshProgress(show: Boolean) {
        postViewAction { swipeToRefresh.isRefreshing = show }
    }

    override fun showEmptyProgress(show: Boolean) {
        zeroViewHolder?.hide()
        fullscreenProgressView.visible(show)
        //trick for disable and hide swipeToRefresh on fullscreen progress
        swipeToRefresh.visible(!show)
        postViewAction { swipeToRefresh.isRefreshing = false }
    }

    override fun showPageProgress(show: Boolean) {
        postViewAction { adapter.showProgress(show) }
    }

    override fun showEmptyView(show: Boolean) {
        if (show) zeroViewHolder?.showEmptyData()
        else zeroViewHolder?.hide()
    }

    override fun showEmptyError(show: Boolean, message: String?) {
        recyclerView.visible(!show)
        if (show) zeroViewHolder?.showEmptyError(message)
        else zeroViewHolder?.hide()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}