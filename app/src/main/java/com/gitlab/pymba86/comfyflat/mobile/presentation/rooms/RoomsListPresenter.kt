package com.gitlab.pymba86.comfyflat.mobile.presentation.rooms

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.room.RoomInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.Paginator
import io.reactivex.Single
import javax.inject.Inject

@InjectViewState
class RoomsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: RoomInteractor
    ) : BasePresenter<RoomsListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        refreshProjects()
    }

    private val paginator = Paginator(
        { getProjectsSingle(it) },
        object : Paginator.ViewController<Room> {
            override fun showEmptyProgress(show: Boolean) {
                viewState.showEmptyProgress(show)
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                if (error != null) {

                } else {
                    viewState.showEmptyError(show, null)
                }
            }

            override fun showErrorMessage(error: Throwable) {

            }

            override fun showEmptyView(show: Boolean) {
                viewState.showEmptyView(show)
            }

            override fun showData(show: Boolean, data: List<Room>) {
                viewState.showRooms(show, data)
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showRefreshProgress(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPageProgress(show)
            }
        }
    )

    private fun getProjectsSingle(page: Int) = Single.fromCallable {interactor.getRooms(page) }

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

    fun onRoomClicked(id: Long, name : String) = router.startFlow(Screens.RoomFlow(id, name))

    fun refreshProjects() = paginator.refresh()
    fun loadNextProjectsPage() = paginator.loadNewPage()

    fun onBackPressed() = router.exit()
}