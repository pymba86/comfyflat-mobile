package com.gitlab.pymba86.comfyflat.mobile.presentation.rooms

import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.entity.Room
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.BasePresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.Paginator
import com.gitlab.pymba86.comfyflat.mobile.toothpick.PrimitiveWrapper
import com.gitlab.pymba86.comfyflat.mobile.toothpick.ProjectListMode
import io.reactivex.Single
import javax.inject.Inject

class RoomsListPresenter @Inject constructor(
    @ProjectListMode private val modeWrapper: PrimitiveWrapper<Int>,
    private val router: FlowRouter
) : BasePresenter<RoomsListView>() {

    companion object {
        const val MAIN_PROJECTS = 0
        const val MY_PROJECTS = 1
        const val STARRED_PROJECTS = 2
    }

    private val mode = modeWrapper.value

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
                viewState.showProjects(show, data)
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showRefreshProgress(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPageProgress(show)
            }
        }
    )

    private fun getProjectsSingle(page: Int) = Single.fromCallable {testRoom()};


    private fun testRoom() =  listOf(Room(1))

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

    fun refreshProjects() = paginator.refresh()
    fun loadNextProjectsPage() = paginator.loadNewPage()

    fun onProjectClicked(id: Long) = null
    fun onBackPressed() = router.exit()
}