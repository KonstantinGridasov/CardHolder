package com.gkreduction.cardholder.ui.fragment.home

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentHomeBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.gkreduction.cardholder.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class.java
), CardClickListener {

    override fun onStart() {
        super.onStart()
        (binding as FragmentHomeBinding).viewModel = viewModel
        (binding as FragmentHomeBinding).itemListener = this
        val args = HomeFragmentArgs.fromBundle(requireArguments())
        var nameToolbar: String? = null
        if (args.category != null) {
            val category = args.category
            viewModel?.sortListByCategory(category)
            nameToolbar = category?.catName
        } else {
            viewModel?.getAllCategories()
        }
        updateNameToolbar(nameToolbar)
    }

    private fun updateNameToolbar(nameToolbar: String?) {
        (activity as? MainActivity)?.updateToolbarName(nameToolbar)
    }

    override fun onItemClick(id: Long) {
        view?.findNavController()?.navigate(HomeFragmentDirections.toCard(id))
    }
}