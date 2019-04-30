package com.gitlab.pymba86.comfyflat.mobile.ui.drawer

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gitlab.pymba86.comfyflat.mobile.R
import com.gitlab.pymba86.comfyflat.mobile.entity.app.session.UserAccount
import com.gitlab.pymba86.comfyflat.mobile.extension.inflate
import com.gitlab.pymba86.comfyflat.mobile.extension.visible
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerPresenter
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem
import com.gitlab.pymba86.comfyflat.mobile.presentation.drawer.NavigationDrawerView.MenuItem.*
import com.gitlab.pymba86.comfyflat.mobile.ui.global.BaseFragment
import com.gitlab.pymba86.comfyflat.mobile.ui.global.MessageDialogFragment
import kotlinx.android.synthetic.main.fragment_nav_drawer.*
import kotlinx.android.synthetic.main.item_user_acount.view.*

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
        nickTV.setOnClickListener { presenter.onUserClick() }
        dropDownImageView.setOnClickListener {
            showAccountsList(accountsContainer.visibility == View.GONE)
        }

        logoutIV.setOnClickListener {
            MessageDialogFragment.create(
                message = getString(R.string.logout_question),
                positive = getString(R.string.exit),
                negative = getString(R.string.cancel),
                tag = CONFIRM_LOGOUT_TAG
            ).show(childFragmentManager, CONFIRM_LOGOUT_TAG)
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

    override fun dialogPositiveClicked(tag: String) {
        when (tag) {
            CONFIRM_LOGOUT_TAG -> presenter.onLogoutClick()
        }
    }

    private fun showAccountsList(show: Boolean) {
        accountsContainer.visible(show)
        dropDownImageView.rotation = if (show) 180f else 0f
    }


    override fun setAccounts(accounts: List<UserAccount>, currentAccount: UserAccount) {
        nickTV.text = currentAccount.userName
        managerKeyTV.text = currentAccount.managerKey

        accountsContainer.removeAllViews()
        accounts.forEach { acc ->
            accountsContainer.inflate(R.layout.item_user_acount)
                .apply {
                    avatarImageView.text = acc.userName.getOrElse(0){'A'}.toUpperCase().toString()
                    nameTextView.text = acc.userName
                    managerKeyTextView.text = acc.managerKey
                    selectorView.visible(acc == currentAccount)
                    setOnClickListener { presenter.onAccountClick(acc) }
                }
                .also {
                    accountsContainer.addView(it)
                }
        }
        accountsContainer.inflate(R.layout.item_add_acount)
            .apply {
                setOnClickListener { presenter.onAddAccountClick() }
            }
            .also {
                accountsContainer.addView(it)
            }
    }

    private companion object {
        private const val CONFIRM_LOGOUT_TAG = "confirm_logout_tag"
    }
}