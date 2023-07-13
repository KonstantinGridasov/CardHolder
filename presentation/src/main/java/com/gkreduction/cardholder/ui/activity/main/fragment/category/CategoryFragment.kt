package com.gkreduction.cardholder.ui.activity.main.fragment.category

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentCategoryBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.OnChangePositionItemListener
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.cardholder.ui.dialog.remove.DialogRemove
import com.gkreduction.domain.entity.Category

class CategoryFragment : BaseFragment<CategoryViewModel>(
    R.layout.fragment_category,
    CategoryViewModel::class.java
), CategoryAdapterClickListener, OnChangePositionItemListener {

    private var category: Category? = null
    private var list: List<Category>? = null

    override fun onStart() {
        super.onStart()
        (binding as FragmentCategoryBinding).viewModel = viewModel
        viewModel?.getAllCategories()
        initListeners()
    }

    private fun initListeners() {
        (binding as FragmentCategoryBinding).listenerClick = this
        (binding as FragmentCategoryBinding).onChangeListener = this
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar()
                .setOnImageClickListener { viewModel?.addNewCategory() }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getButton().setOnClickListener {
                list?.let { viewModel?.updatePosition(it) }
                navigateToBack(category)
            }
        }
    }


    override fun onChoose(category: Category?) {
        this.category = category
//        navigateToBack(category)
    }

    override fun addCategory(string: String) {
        viewModel?.saveCategory(string)
    }

    override fun updateCategory(category: Category) {
        viewModel?.updateCategory(category)

    }

    override fun removeCategory(category: Category) {
        if (activity is MainActivity)
            (activity as MainActivity).let {
                val dialog = DialogRemove()
                dialog.setListener(category.catName, false) { isDell ->
                    if (isDell)
                        viewModel?.removeCategory(category)
                }
                dialog.show(it.supportFragmentManager, "")
            }


    }

    private fun navigateToBack(category: Category?) {
        view?.findNavController()
            ?.navigate(CategoryFragmentDirections.toAdd(category))
    }


    override fun onChange(items: List<Category>?) {
        if (items != null) {
            for (i in items.indices) {
                items[i].position = i
            }
            this.list = items
        }

    }
}