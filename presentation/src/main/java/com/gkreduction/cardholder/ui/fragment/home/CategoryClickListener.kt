package com.gkreduction.cardholder.ui.fragment.home

import com.gkreduction.domain.entity.Category

interface CategoryClickListener {
    fun onItemClick(category: Category?)
}