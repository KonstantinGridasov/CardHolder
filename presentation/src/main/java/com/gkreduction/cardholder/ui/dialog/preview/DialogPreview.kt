package com.gkreduction.cardholder.ui.dialog.preview

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogPreviewBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment
import com.gkreduction.domain.entity.ScanCode

class DialogPreview : BaseDialogFragment<DialogPreviewViewModel>(
    R.layout.dialog_preview, DialogPreviewViewModel::class.java

) {
    private var listener: ((Boolean) -> Unit)? = null
    private var scanCode: ScanCode? = null

    override fun initialized() {
        super.initialized()
        initListener()
    }


    override fun onResume() {
        super.onResume()
        (binding as DialogPreviewBinding).barcode.scanCode = scanCode
    }

    fun setListener(scanCode: ScanCode, listener: (Boolean) -> Unit) {
        this.scanCode = scanCode
        this.listener = listener

    }

    private fun initListener() {
        (binding as DialogPreviewBinding).ok.setOnClickListener {
            listener?.invoke(true)
            dismiss()
        }
        (binding as DialogPreviewBinding).cancel.setOnClickListener {
            listener?.invoke(false)
            dismiss()
        }
    }


}