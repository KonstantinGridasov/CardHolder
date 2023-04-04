package com.gkreduction.cardholder.ui.activity.edit_card

import androidx.databinding.BindingAdapter
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.ui.widjet.MyCardView
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.ScanCode

object BindingCard {
    @JvmStatic
    @BindingAdapter(
        "set_barcode",
        requireAll = false
    )
    fun setSetBarcode(
        view: BarcodeView,
        barcode: ScanCode?,
    ) {
        barcode?.let {
            view.scanCode = it
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
