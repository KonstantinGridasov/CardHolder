package com.gkreduction.cardholder.ui.activity.card

import androidx.databinding.BindingAdapter
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.ui.widjet.MyCardView
import com.gkreduction.domain.entity.Card

object BindingCard {
    @JvmStatic
    @BindingAdapter(
        "set_card",
        "set_status_revert",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: BarcodeView,
        card: Card?,
        status_revert: Boolean?
    ) {
        card?.let { item ->
            if (status_revert != null && status_revert && item.existSecondary) {
                item.secondary?.let { view.scanCode = it }
            } else
                item.primary?.let { view.scanCode = it }
            
        }
    }

    @JvmStatic
    @BindingAdapter(
        "set_color_gradient",
        "bottom_to_top",
        requireAll = false
    )
    fun setColorGradient(
        view: MyCardView,
        card: Card?,
        bottom_to_top: Boolean?

    ) {
        card?.let {
            if (bottom_to_top == true)
                view.setBottomTopOrientation()
            view.changeColor(it.colorStart, it.colorEnd)
        }
    }

}
