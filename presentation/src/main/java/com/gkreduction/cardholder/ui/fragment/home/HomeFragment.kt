package com.gkreduction.cardholder.ui.fragment.home

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentHomeBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.domain.entity.Category

class HomeFragment : BaseFragment<HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class.java
), CardClickListener, CategoryClickListener {

    override fun onStart() {
        super.onStart()
        (binding as FragmentHomeBinding).viewModel = viewModel
        (binding as FragmentHomeBinding).itemListener = this
        (binding as FragmentHomeBinding).categoryClick = this
        viewModel?.getAllCategories()
        initListener()

    }

    private fun initListener() {
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar().setOnImageClickListener { navigateToInfo() }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getButton().setOnClickListener {
                navigateToAdd()
            }
        }

    }

    private fun navigateToInfo() {
        view?.findNavController()?.navigate(HomeFragmentDirections.toInfo())
    }

    private fun navigateToAdd() {
        view?.findNavController()?.navigate(R.id.addFragment)
    }

    override fun onItemClick(id: Long) {
        view?.findNavController()?.navigate(HomeFragmentDirections.toCard(id))
    }

    override fun onItemClick(category: Category?) {
        viewModel?.updateCardByCategory(category)
    }
}