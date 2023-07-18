package com.gkreduction.cardholder.ui.dialog.edit

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogEditBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment
import com.gkreduction.cardholder.utils.showKeyboard
import com.gkreduction.domain.entity.Category

class DialogEdit : BaseDialogFragment<DialogEditViewModel>(
    R.layout.dialog_edit, DialogEditViewModel::class.java

) {
    private var listener: ((Category) -> Unit)? = null
    private var category: Category? = null
    private var isRename: Boolean = false

    override fun initialized() {
        super.initialized()
        initListener()
    }


    override fun onResume() {
        super.onResume()
        (binding as DialogEditBinding).tvName.text = getText()
        showKeyboard((binding as DialogEditBinding).etCategoryName)
    }

    fun setListener(category: Category?, listener: (Category) -> Unit) {
        this.category = category
        this.isRename = category != null
        this.listener = listener

    }

    private fun initListener() {
        (binding as DialogEditBinding).ok.setOnClickListener {
            if (checkText())
                listener?.invoke(updateCategory())
            dismiss()
        }
        (binding as DialogEditBinding).cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun getText(): String {
        return if (isRename)
            "${resources.getText(R.string.rename_category)} " +
                    " \"${category?.catName}\" "
        else
            "${resources.getText(R.string.add_category)} "

    }

    private fun checkText(): Boolean {
        val text = (binding as DialogEditBinding).etCategoryName.text.toString()
        return text.isNotEmpty()
    }

    private fun updateCategory(): Category {
        val text = (binding as DialogEditBinding).etCategoryName.text.toString()
        return if (isRename) {
            category!!.catName = text
            category!!
        } else
            Category(catName = text)
    }

}