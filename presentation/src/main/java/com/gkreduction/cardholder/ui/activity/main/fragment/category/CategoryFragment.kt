package com.gkreduction.cardholder.ui.activity.main.fragment.category

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentCategoryBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.AdapterCategoryList
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.OnChangePositionItemListener
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.cardholder.ui.dialog.edit.DialogEdit
import com.gkreduction.cardholder.ui.dialog.remove.DialogRemove
import com.gkreduction.domain.entity.Category

class CategoryFragment : BaseFragment<CategoryViewModel>(
    R.layout.fragment_category,
    CategoryViewModel::class.java
), CategoryAdapterClickListener, OnChangePositionItemListener {

    private var category: Category? = null
    private var list: List<Category>? = null
    private var mode: ModeCategory = ModeCategory.CREATE

    private enum class ModeCategory {
        EDIT,
        CREATE
    }

    override fun onStart() {
        super.onStart()
        (binding as FragmentCategoryBinding).viewModel = viewModel
        viewModel?.getAllCategories()
        initListeners()
    }


    override fun onChoose(category: Category?) {
        this.category = category
    }

    override fun editCategory(category: Category) {
        mode = ModeCategory.EDIT
        showDialogUpdateCategory(category)
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

    override fun onChange(items: List<Category>?) {
        if (items != null) {
            for (i in items.indices) {
                items[i].position = i
            }
            this.list = items
        }

    }

    private fun initListeners() {
        (binding as FragmentCategoryBinding).listenerClick = this
        (binding as FragmentCategoryBinding).onChangeListener = this
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar()
                .setOnImageClickListener {
                    mode = ModeCategory.CREATE
                    showDialogUpdateCategory(null)
                }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getButton().setOnClickListener {
                list?.let { viewModel?.updatePosition(it) }
                navigateToBack(category)
            }
        }
    }


    private fun showDialogUpdateCategory(category: Category?) {
        if (activity is MainActivity)
            (activity as MainActivity).let {
                val dialog = DialogEdit()
                dialog.setListener(category) { category ->
                    when (mode) {
                        ModeCategory.CREATE -> viewModel?.createCategory(category.catName)
                        ModeCategory.EDIT -> viewModel?.updateCategory(category)
                    }

                }
                dialog.show(it.supportFragmentManager, "")

            }
    }


    private fun navigateToBack(category: Category?) {
        view?.findNavController()
            ?.navigate(CategoryFragmentDirections.toAdd(category))
    }


}