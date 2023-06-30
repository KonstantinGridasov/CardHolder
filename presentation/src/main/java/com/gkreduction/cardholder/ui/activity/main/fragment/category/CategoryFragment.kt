package com.gkreduction.cardholder.ui.activity.main.fragment.category

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentCategoryBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.cardholder.ui.dialog.DialogInfo
import com.gkreduction.domain.entity.Category

class CategoryFragment : BaseFragment<CategoryViewModel>(
    R.layout.fragment_category,
    CategoryViewModel::class.java
), CategoryAdapterClickListener {

    private var category: Category? = null

    override fun onStart() {
        super.onStart()
        (binding as FragmentCategoryBinding).viewModel = viewModel
        viewModel?.getAllCategories()
        initListeners()
    }

    private fun initListeners() {
        (binding as FragmentCategoryBinding).listenerClick = this
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar()
                .setOnImageClickListener { viewModel?.addNewCategory() }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getButton().setOnClickListener {
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
                val dialog = DialogInfo()
                dialog.setListener(category.catName) { isDell ->
                    if (isDell) viewModel?.removeCategory(
                        category
                    )
                }
                dialog.show(it.supportFragmentManager, "")
            }


    }

    private fun navigateToBack(category: Category?) {
        when (view?.findNavController()?.previousBackStackEntry?.destination?.id) {
            R.id.homeFragment ->
                view?.findNavController()
                    ?.navigate(CategoryFragmentDirections.toHome(category))
            R.id.addFragment ->
                view?.findNavController()
                    ?.navigate(CategoryFragmentDirections.toAdd(category))

            else -> view?.findNavController()?.navigateUp()
        }

    }
}