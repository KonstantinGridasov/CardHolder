package com.gkreduction.cardholder.ui.activity.main.adapter

import com.gkreduction.domain.entity.Category

interface CategoryClickListener {
    fun onItemClick(category: Category?)
}