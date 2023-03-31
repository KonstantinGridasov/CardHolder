package com.gkreduction.cardholder.ui.dialog

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogCategoryBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment
import com.gkreduction.cardholder.ui.dialog.adapter.CategoryClickListener
import com.gkreduction.domain.entity.Category

class CategoryDialog : BaseDialogFragment<CategoryDialogViewModel>(
    R.layout.dialog_category,
    CategoryDialogViewModel::class.java
), CategoryClickListener {
    private var listener: ((Category?) -> Unit)? = null

    override fun initialized() {
        super.initialized()
        (binding as DialogCategoryBinding).viewModel = viewModel
        (binding as DialogCategoryBinding).listenerClick = this
        viewModel?.getAllCategories()
    }

    fun setListener(listener: (Category?) -> Unit) {
        this.listener = listener
    }


    override fun onItemClick(category: Category?) {
        listener?.invoke(category)
        viewModel?.updateCategory(category)
    }
}