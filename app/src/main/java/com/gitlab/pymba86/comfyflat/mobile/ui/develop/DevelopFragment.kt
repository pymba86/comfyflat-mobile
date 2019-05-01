package com.gitlab.pymba86.comfyflat.mobile.ui.develop

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppDeveloper
import com.gitlab.pymba86.comfyflat.mobile.presentation.develop.DevelopPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.develop.DevelopView
import com.gitlab.pymba86.comfyflat.mobile.toothpick.DI
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.global.list.AppDevelopAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_developers.*

class DevelopFragment : BaseFragment(), DevelopView {
    override val layoutRes = R.layout.fragment_developers

    override val parentScopeName = DI.APP_SCOPE

    private val adapter: LibraryAdapter by lazy { LibraryAdapter() }

    @InjectPresenter
    lateinit var presenter: DevelopPresenter

    @ProvidePresenter
    fun providePresenter(): DevelopPresenter =
        scope.getInstance(DevelopPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        with(recyclerView) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@DevelopFragment.adapter
        }
    }

    override fun showDevelopers(libraries: List<AppDeveloper>) {
        adapter.setData(libraries)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    private inner class LibraryAdapter : ListDelegationAdapter<MutableList<Any>>() {
        init {
            items = mutableListOf()
            delegatesManager.addDelegate(AppDevelopAdapterDelegate { })
        }

        fun setData(libraries: List<AppDeveloper>) {
            items.clear()
            items.addAll(libraries)

            notifyDataSetChanged()
        }
    }
}