package com.gkreduction.cardholder.ui.activity.card

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.domain.entity.Card
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

object BindingCard {
    @JvmStatic
    @BindingAdapter(
        "set_card",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: BarcodeView,
        card: Card?,
    ) {
        card?.let {
            view.scanCode = card.primary
        }
    }

}
