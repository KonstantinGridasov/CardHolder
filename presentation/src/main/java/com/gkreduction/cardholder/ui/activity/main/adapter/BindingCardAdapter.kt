package com.gkreduction.cardholder.ui.activity.main.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.domain.entity.Card

object BindingCardAdapter {
    @JvmStatic
    @BindingAdapter(
        "items_cards",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: RecyclerView,
        items: List<Card>?,
    ) {
        items?.let {
            val adapter = AdapterCard()
            adapter.updateItems(it)
            view.adapter = adapter
        }

    }
}