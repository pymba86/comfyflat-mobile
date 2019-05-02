package com.gitlab.pymba86.comfyflat.mobile.ui.auth

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.gitlab.pymba86.comfyflat.mobile.BuildConfig
import com.gitlab.pymba86.comfyflat.mobile.R
import kotlinx.android.synthetic.main.fragment_custom_server_auth.*

class CustomServerAuthFragment : BottomSheetDialogFragment() {

    private lateinit var listener: OnClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = parentFragment as OnClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_custom_server_auth, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realmValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                connect()
                true
            } else {
                false
            }
        }

        submitButton.setOnClickListener { connect() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun connect() {

        val url = serverPathValue.text.toString()
        val realm = realmValue.text.toString()

        if (realm.isEmpty()) {
            Toast.makeText(this.context, getString(R.string.empty_realm), Toast.LENGTH_SHORT).show()
            return
        }

        if (url.isEmpty()) {
            Toast.makeText(this.context, getString(R.string.empty_server_url), Toast.LENGTH_SHORT).show()
            return
        }

        listener.customConnect.invoke(url, realm)
        dismiss()
    }

    interface OnClickListener {
        val customConnect: (url: String, realm: String) -> Unit
    }
}