package com.gkreduction.cardholder.ui.activity.main.adapter

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.ui.dialog.adapter.CategoryClickListener
import com.gkreduction.cardholder.ui.dialog.adapter.EditTextListener
import com.gkreduction.domain.entity.Category
import com.google.android.flexbox.*


object BindingCategoryMain {
    @JvmStatic
    @BindingAdapter(
        "list_categories_main",
        "item_categories_main",
        "category_choose",
        requireAll = false
    )
    fun setCategoriesMain(
        view: RecyclerView,
        items: List<Category>?,
        category: Category?,
        listener: CategoryClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryMain(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter

            val layoutManager = FlexboxLayoutManager(view.context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
            view.layoutManager = layoutManager

        }

    }



}