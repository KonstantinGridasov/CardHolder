package com.gkreduction.cardholder.ui.dialog

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogInfoBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment

class DialogInfo : BaseDialogFragment<DialogInfoViewModel>(
    R.layout.dialog_info,
    DialogInfoViewModel::class.java

) {
    private var listener: ((Boolean) -> Unit)? = null
    private var info: String = ""

    override fun initialized() {
        super.initialized()
        initListener()
    }

    private fun initListener() {
        (binding as DialogInfoBinding).ok.setOnClickListener {
            listener?.invoke(true)
            dismiss()
        }
        (binding as DialogInfoBinding).cancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val text = "${resources.getText(R.string.text_dialog_info)} $info ?"
        (binding as DialogInfoBinding).textToolbar.text = text
    }

    fun setListener(info: String, listener: (Boolean) -> Unit) {
        this.info = info
        this.listener = listener

    }


}