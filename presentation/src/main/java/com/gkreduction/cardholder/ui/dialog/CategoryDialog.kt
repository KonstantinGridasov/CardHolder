package com.gkreduction.cardholder.ui.dialog

import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.DialogCategoryBinding
import com.gkreduction.cardholder.ui.base.BaseDialogFragment

class CategoryDialog : BaseDialogFragment<CategoryDialogViewModel>(
    R.layout.dialog_category,
    CategoryDialogViewModel::class.java
) {
    override fun initialized() {
        super.initialized()
        (binding as DialogCategoryBinding).viewModel = viewModel
        viewModel?.getAllCategories()
    }
}