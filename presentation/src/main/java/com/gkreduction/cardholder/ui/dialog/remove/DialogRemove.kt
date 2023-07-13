package com.gkreduction.cardholder.ui.dialog.remove

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogRemoveBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment

class DialogRemove : BaseDialogFragment<DialogRemoveViewModel>(
    R.layout.dialog_remove, DialogRemoveViewModel::class.java

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
        (binding as DialogRemoveBinding).tvDialog.text = getText()
    }

    fun setListener(info: String, isCard: Boolean, listener: (Boolean) -> Unit) {
        this.info = info
        this.isCard = isCard
        this.listener = listener

    }

    private fun initListener() {
        (binding as DialogRemoveBinding).ok.setOnClickListener {
            listener?.invoke(true)
            dismiss()
        }
        (binding as DialogRemoveBinding).cancel.setOnClickListener {
            listener?.invoke(false)
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