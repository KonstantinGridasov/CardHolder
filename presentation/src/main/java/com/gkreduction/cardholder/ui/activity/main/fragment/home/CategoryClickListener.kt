package com.gkreduction.cardholder.ui.activity.main.fragment.home

import com.gkreduction.domain.entity.Category

interface CategoryClickListener {
    fun onItemClick(category: Category?)
}