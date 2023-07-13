package com.gkreduction.cardholder.ui.dialog.settings

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogRemoveBinding
import com.gkreduction.cardholder.databinding.DialogSettingsBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment

class DialogSettings : BaseDialogFragment<DialogSettingsViewModel>(
    R.layout.dialog_settings, DialogSettingsViewModel::class.java

) {
    private var listener: ((Boolean) -> Unit)? = null

    override fun initialized() {
        super.initialized()
        initListener()
    }

    fun setListener(listener: (Boolean) -> Unit) {
        this.listener = listener

    }

    private fun initListener() {
        (binding as DialogSettingsBinding).ok.setOnClickListener {
            listener?.invoke(true)
            dismiss()
        }
        (binding as DialogSettingsBinding).cancel.setOnClickListener {
            listener?.invoke(false)
            dismiss()
        }
    }


}