package com.gkreduction.cardholder.ui.dialog

import android.util.Log
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogInfoBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment
import com.gkreduction.domain.entity.Category

class DialogInfo : BaseDialogFragment<DialogInfoViewModel>(
    R.layout.dialog_info,
    DialogInfoViewModel::class.java
) {
    private var listener: ((Category) -> Unit)? = null
    private var category: Category? = null
    override fun initialized() {
        super.initialized()
        initListener()
    }

    private fun initListener() {
        (binding as DialogInfoBinding).ok.setOnClickListener {
            Log.d("TEST_CategoryViewModel","setOnClickListener")
            category?.let {
                listener?.invoke(it)
            }
            dismiss()
        }
        (binding as DialogInfoBinding).cancel.setOnClickListener {
            dismiss()
        }
    }

    fun setListener(category: Category, listener: (Category) -> Unit) {
        this.listener = listener
        this.category = category
    }


}