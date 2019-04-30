package com.gitlab.pymba86.comfyflat.mobile.ui.auth

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        if (serverPathValue.text.isEmpty()) {
            serverPathValue.setText(BuildConfig.ORIGIN_GITLAB_ENDPOINT)
        }

        submitButton.setOnClickListener { login() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun login() {
        val url = serverPathValue.text.toString()
        listener.customLogin.invoke(url)
        dismiss()
    }

    interface OnClickListener {
        val customLogin: (url: String) -> Unit
    }
}