package com.gkreduction.cardholder.ui.dialog

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogInfoBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment

class DialogInfo : BaseDialogFragment<DialogInfoViewModel>(
    R.layout.dialog_info, DialogInfoViewModel::class.java

) {
    private var listener: ((Boolean) -> Unit)? = null
    private var info: String = ""
    private var isCard: Boolean = false

    override fun initialized() {
        super.initialized()
        initListener()
    }


    override fun onResume() {
        super.onResume()
        (binding as DialogInfoBinding).tvDialog.text = getText()
    }

    fun setListener(info: String, isCard: Boolean, listener: (Boolean) -> Unit) {
        this.info = info
        this.isCard = isCard
        this.listener = listener

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

    private fun getText(): String {
        return if (isCard)
            "${resources.getText(R.string.text_dialog_info)} " +
                    "${resources.getText(R.string.text_dialog_info_card)}"
        else
            "${resources.getText(R.string.text_dialog_info)} " +
                    "${resources.getText(R.string.text_dialog_info_category)} " +
                    " \"$info\" ? "

    }


}