package com.gitlab.pymba86.comfyflat.mobile.ui.rooms

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.Screens
import com.gitlab.pymba86.comfyflat.mobile.model.system.flow.FlowRouter
import com.gitlab.pymba86.comfyflat.mobile.presentation.global.GlobalMenuController
import com.gitlab.pymba86.comfyflat.mobile.presentation.rooms.RoomsListPresenter
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_rooms_container.*
import toothpick.Toothpick
import javax.inject.Inject

class RoomsContainerFragment : BaseFragment() {

    @Inject
    lateinit var router: FlowRouter

    override val layoutRes = R.layout.fragment_rooms_container

    private val adapter: ProjectsPagesAdapter by lazy { ProjectsPagesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        router.exit()
    }

    private inner class ProjectsPagesAdapter : FragmentPagerAdapter(childFragmentManager) {
        override fun getItem(position: Int) = when (position) {
            0 -> Screens.Rooms(RoomsListPresenter.MAIN_PROJECTS).fragment
            else -> null
        }

        override fun getCount() = 3

        override fun getPageTitle(position: Int) = when (position) {
            0 -> getString(R.string.all_projects_title)
            1 -> getString(R.string.my_projects_title)
            2 -> getString(R.string.starred_projects_title)
            else -> null
        }
    }
}