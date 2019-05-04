package com.gitlab.pymba86.comfyflat.mobile.presentation.rooms

import com.arellomobile.mvp.InjectViewState
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.model.interactor.room.RoomInteractor
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.Paginator
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.WampState
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class RoomsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val roomInteractor: RoomInteractor
) : BasePresenter<RoomsListView>() {

    private var wampClientStateDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        wampClientStateDisposable = roomInteractor.stateWampClient()
            .subscribe { state ->
                when (state) {
                    WampState.CONNECTING -> {
                        viewState.showEmptyProgress(true)
                      //  viewState.showEmptyProgress(true)
                    }
                    WampState.CLOSED -> {
                    }
                    WampState.CLOSING -> {
                     //   viewState.showEmptyProgress(false)
                    //    viewState.showEmptyError(true, "Connection closing")
                    }
                    WampState.OPEN -> {
                        refreshProjects()
                    }
                }
            }
    }

    private val paginator = Paginator(
        { page -> roomInteractor.getRooms(page) },
        object : Paginator.ViewController<Room> {
            override fun showEmptyProgress(show: Boolean) {
                viewState.showEmptyProgress(show)
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.showEmptyProgress(!show)
                if (error != null) {
                    // errorHandler.proceed(error, { viewState.showEmptyError(show, it) })
                    viewState.showEmptyError(show, "showEmptyError")
                } else {
                    viewState.showEmptyError(show, null)
                }
            }

            override fun showErrorMessage(error: Throwable) {
                viewState.showEmptyProgress(false)
                viewState.showEmptyError(true, "showErrorMessage")
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

    override fun onDestroy() {
        super.onDestroy()
        wampClientStateDisposable?.dispose()
        paginator.release()
    }

    fun onRoomClicked(id: Int, name: String) = router.startFlow(Screens.RoomFlow(id, name))

    fun refreshProjects() = paginator.refresh()
    fun loadNextProjectsPage() = paginator.loadNewPage()

    fun onBackPressed() = router.exit()
}