package com.gkreduction.cardholder.ui.fragment.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.ui.fragment.home.CardClickListener
import com.gkreduction.cardholder.ui.fragment.home.CategoryClickListener
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category

object BindingHome {

    @JvmStatic
    @BindingAdapter("items_cards", "item_listener", requireAll = false)
    fun setListCardHome(
        view: RecyclerView, items: List<Card>?, listener: CardClickListener?
    ) {
        items?.let {
            val adapter = AdapterCard(listener)
            adapter.updateItems(it)
            view.adapter = adapter

        }
    }


    @JvmStatic
    @BindingAdapter(
        "list_categories_main",
        "item_categories_main",
        "category_choose",
        requireAll = false
    )
    fun setCategoriesToMain(
        view: RecyclerView, items: List<Category>?,
        category: Category?, listener: CategoryClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryMain(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter

        }
    }


}