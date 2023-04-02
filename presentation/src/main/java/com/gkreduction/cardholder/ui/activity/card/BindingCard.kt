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
        card?.let {
            if (status_revert != null && status_revert && it.existSecondary) {
                view.scanCode = card.secondary
            } else
                view.scanCode = card.primary


        }
    }

    @JvmStatic
    @BindingAdapter(
        "set_color_gradient",
        requireAll = false
    )
    fun setColorGradient(
        view: MyCardView,
        card: Card?,
    ) {
        card?.let {
            view.setBottomTopOrientation()
            view.changeColor(it.colorStart, it.colorEnd)
        }
    }

}
