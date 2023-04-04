package com.gkreduction.cardholder.ui.activity.category.adapter

import com.gkreduction.domain.entity.Category

interface CategoryAdapterClickListener {
    fun onChoose(category: Category?)

    fun addCategory(string: String)

    fun updateCategory(category: Category)

    fun removeCategory(category: Category)


}