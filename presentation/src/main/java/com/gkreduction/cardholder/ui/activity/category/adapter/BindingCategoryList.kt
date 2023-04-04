package com.gkreduction.cardholder.ui.activity.category.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.domain.entity.Category


object BindingCategoryList {
    @JvmStatic
    @BindingAdapter(
        "list_items",
        "item_chooses",
        "list_items_click",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: RecyclerView,
        items: List<Category>?,
        category: Category?,
        listener: CategoryAdapterClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryList(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter
        }

    }


}