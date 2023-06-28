package com.gkreduction.cardholder.ui.fragment.home

import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentHomeBinding
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.gkreduction.cardholder.ui.activity.main.adapter.CategoryClickListener
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

    }


    override fun onItemClick(id: Long) {
        view?.findNavController()?.navigate(HomeFragmentDirections.toCard(id))
    }

    override fun onItemClick(category: Category?) {
        viewModel?.updateCardByCategory(category)
    }
}