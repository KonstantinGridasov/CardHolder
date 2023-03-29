package com.gkreduction.cardholder.ui.activity.main.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.domain.entity.Card
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

object BindingCardAdapter {
    @JvmStatic
    @BindingAdapter(
        "items_cards",
        "item_listener",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: CarouselRecyclerview,
        items: List<Card>?,
        listener: CardClickListener?
    ) {
        items?.let {
            val adapter = AdapterCard(listener)
            adapter.updateItems(it)
            view.adapter = adapter
            view.apply {
                set3DItem(false)
                setAlpha(false)
                setInfinite(true)
            }
        }

    }
}