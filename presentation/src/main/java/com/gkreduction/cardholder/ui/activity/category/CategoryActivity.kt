package com.gkreduction.cardholder.ui.activity.category

import android.content.Intent
import android.os.Bundle
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CATEGORY
import com.gkreduction.cardholder.databinding.ActivityCategoryBinding
import com.gkreduction.cardholder.ui.activity.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.dialog.DialogInfo
import com.gkreduction.domain.entity.Category


class CategoryActivity :
    BaseActivity<CategoryViewModel>(R.layout.activity_category, CategoryViewModel::class.java),
    CategoryAdapterClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (binding as ActivityCategoryBinding).viewModel = viewModel
        initListeners()
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllCategories()
    }


    private fun initListeners() {
        (binding as ActivityCategoryBinding).listenerClick = this
    }


    override fun onChoose(category: Category?) {
        navigateToBack(category)
    }

    override fun addCategory(string: String) {
        viewModel.saveCategory(string)
    }

    override fun updateCategory(category: Category) {
        viewModel.updateCategory(category)

    }

    override fun removeCategory(category: Category) {
        val dialog = DialogInfo()
        dialog.setListener(category.catName) { if (it) viewModel.removeCategory(category) }
        dialog.show(supportFragmentManager, "")


    }

    private fun navigateToBack(category: Category?) {
        val returnIntent = Intent()
        if (category != null) {
            returnIntent.putExtra(CATEGORY, category)
            setResult(RESULT_OK, returnIntent)
        } else setResult(RESULT_CANCELED, returnIntent)
        finish()
    }
}

