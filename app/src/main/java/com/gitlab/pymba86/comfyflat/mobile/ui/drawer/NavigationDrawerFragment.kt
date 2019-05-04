package com.gitlab.pymba86.comfyflat.mobile.ui.drawer

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.gitlab.pymba86.comfyflat.mobile.extension.visible
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem.*
import com.gitlab.pymba86.comfyflat.mobile.toothpick.client.WampState
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.global.MessageDialogFragment
import kotlinx.android.synthetic.main.fragment_nav_drawer.*
import kotlinx.android.synthetic.main.item_session.view.*

class NavigationDrawerFragment : BaseFragment(), NavigationDrawerView, MessageDialogFragment.OnClickListener {

    override val layoutRes = R.layout.fragment_nav_drawer

    private val itemClickListener = { view: View ->
        presenter.onMenuItemClick(view.tag as MenuItem)
    }

    @InjectPresenter
    lateinit var presenter: NavigationDrawerPresenter

    @ProvidePresenter
    fun providePresenter(): NavigationDrawerPresenter =
        scope.getInstance(NavigationDrawerPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showAccountsList(false)
        realmTV.setOnClickListener { presenter.onSessionClick() }
        dropDownImageView.setOnClickListener {
            showAccountsList(sessionsContainer.visibility == View.GONE)
        }

        disconnectIV.setOnClickListener {
            MessageDialogFragment.create(
                message = getString(R.string.logout_question),
                positive = getString(R.string.exit),
                negative = getString(R.string.cancel),
                tag = CONFIRM_REMOVE_SESSION_TAG
            ).show(childFragmentManager, CONFIRM_REMOVE_SESSION_TAG)
        }

        roomsMI.tag = ROOMS
        aboutMI.tag = ABOUT

        roomsMI.setOnClickListener(itemClickListener)
        aboutMI.setOnClickListener(itemClickListener)
    }

    override fun selectMenuItem(item: MenuItem) {
        (0 until navDrawerMenuContainer.childCount)
            .map { navDrawerMenuContainer.getChildAt(it) }
            .forEach { menuItem -> menuItem.tag?.let { menuItem.isSelected = it == item } }
    }

    fun onScreenChanged(item: MenuItem) {
        presenter.onScreenChanged(item)
    }

    fun updateStateWampClient(state: WampState?) {
        stateClientTV.text = when(state) {
            WampState.CONNECTING -> "connecting"
            WampState.CLOSED -> "closed"
            WampState.CLOSING -> "closing"
            WampState.OPEN -> "open"
            else -> "undefined"
        }
    }

    private fun showAccountsList(show: Boolean) {
        sessionsContainer.visible(show)
        dropDownImageView.rotation = if (show) 180f else 0f
    }


    override fun setSessions(sessions: List<Session>, currentSession: Session) {
        realmTV.text = currentSession.realm

        sessionsContainer.removeAllViews()
        sessions.forEach { item ->
            sessionsContainer.inflate(R.layout.item_session)
                .apply {
                    avatarImageView.text = item.realm.getOrElse(0){'A'}.toUpperCase().toString()
                    nameTextView.text = item.realm
                    selectorView.visible(item == currentSession)
                    setOnClickListener { presenter.onSessionClick(item) }
                }
                .also {
                    sessionsContainer.addView(it)
                }
        }
        sessionsContainer.inflate(R.layout.item_add_session)
            .apply {
                setOnClickListener { presenter.onAddSessionClick() }
            }
            .also {
                sessionsContainer.addView(it)
            }
    }

    override fun dialogPositiveClicked(tag: String) {
        when (tag) {
            CONFIRM_REMOVE_SESSION_TAG -> presenter.onRemoveSessionClick()
        }
    }

    private companion object {
        private const val CONFIRM_REMOVE_SESSION_TAG = "confirm_remove_session_tag"
    }
}