package com.gitlab.pymba86.comfyflat.mobile.ui.rooms

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.extension.showSnackMessage
import com.gitlab.pymba86.comfyflat.mobile.extension.visible
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import com.gitlab.pymba86.comfyflat.mobile.presentation.rooms.RoomsListPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.rooms.RoomsListView
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.global.ZeroViewHolder
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_base_list.*
import kotlinx.android.synthetic.main.layout_zero.*
import toothpick.Toothpick
import javax.inject.Inject

class RoomsListFragment : BaseFragment(), RoomsListView {

    @Inject
    lateinit var menuController: GlobalMenuController

    @Inject
    lateinit var router: FlowRouter

    override val layoutRes = R.layout.fragment_rooms

    @InjectPresenter
    lateinit var presenter: RoomsListPresenter

    private var zeroViewHolder: ZeroViewHolder? = null

    private val adapter = RoomsAdapter({ presenter.loadNextProjectsPage() }, { id, name -> presenter.onRoomClicked(id, name) })

    @ProvidePresenter
    fun createPresenter(): RoomsListPresenter =
        scope.getInstance(RoomsListPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener { menuController.open() }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        swipeToRefresh.setOnRefreshListener { presenter.refreshProjects() }
        zeroViewHolder = ZeroViewHolder(zeroLayout) { presenter.refreshProjects() }
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

    override fun showEmptyView(show: Boolean) {
        if (show) zeroViewHolder?.showEmptyData()
        else zeroViewHolder?.hide()
    }

    override fun showEmptyError(show: Boolean, message: String?) {
        recyclerView.visible(!show)
        if (show) zeroViewHolder?.showEmptyError(message)
        else zeroViewHolder?.hide()
    }

    override fun showRooms(show: Boolean, projects: List<Room>) {
        zeroViewHolder?.hide()
        fullscreenProgressView.visible(!show)
        recyclerView.visible(show)
        postViewAction { adapter.setData(projects) }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun showPageProgress(show: Boolean) {
        postViewAction { adapter.showProgress(show) }
    }

    override fun onBackPressed() = presenter.onBackPressed()

}