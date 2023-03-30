package com.gkreduction.cardholder.ui.dialog.adapter

import com.gkreduction.domain.entity.Category

interface CategoryClickListener {
    fun onItemClick(category: Category?)
}