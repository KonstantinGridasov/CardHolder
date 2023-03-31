package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.cardview.widget.CardView


class MyCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {
    private var orientation = GradientDrawable.Orientation.LEFT_RIGHT

    fun changeColor(colorStart: Int, colorEnd: Int) {
        try {
            val colors = intArrayOf(colorStart, colorEnd)
            val gradientDrawable = GradientDrawable(
                orientation, colors
            )
            gradientDrawable.colors = colors
            gradientDrawable.cornerRadius = radius
            background = gradientDrawable
        } catch (e: Exception) {
            setCardBackgroundColor(colorStart)
        }

    }

    fun setBottomTopOrientation() {
        orientation = GradientDrawable.Orientation.BOTTOM_TOP
    }

}

