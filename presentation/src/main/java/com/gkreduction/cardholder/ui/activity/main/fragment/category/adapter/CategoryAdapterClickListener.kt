package com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter

import com.gkreduction.domain.entity.Category

interface CategoryAdapterClickListener {
    fun onChoose(category: Category?)

    fun editCategory(category: Category)

    fun removeCategory(category: Category)


}