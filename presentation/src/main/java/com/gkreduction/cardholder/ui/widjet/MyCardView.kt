package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.cardview.widget.CardView


class MyCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {
    private var orientationOne = GradientDrawable.Orientation.LEFT_RIGHT
    private var orientationSecond = GradientDrawable.Orientation.TOP_BOTTOM
    private var drawable: Drawable = getDefaultDrawable()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(0, 0, width , height )
        drawable.draw(canvas)
    }


    fun changeColor(first: Int,second:Int) {
        try {
            drawable = createLayerDrawable(first,second)
            invalidate()
        } catch (e: Exception) {
            setCardBackgroundColor(Color.BLACK)
        }
    }

    fun revertOrientation() {
        orientationOne = GradientDrawable.Orientation.BOTTOM_TOP
        orientationSecond = GradientDrawable.Orientation.RIGHT_LEFT
    }


    private fun createLayerDrawable(color: Int, second: Int): LayerDrawable {
        val drawableArray = arrayOf(
            createGradientDrawable(true, color,second),
            createGradientDrawable(false, color,second)
        )
        return LayerDrawable(drawableArray)
    }


    private fun getDefaultDrawable(): Drawable {
        return createGradientDrawable(false, Color.BLACK, Color.WHITE)
    }

    private fun createGradientDrawable(isFirst: Boolean, color: Int, second: Int): GradientDrawable {
        val gradient = GradientDrawable()
        if (isFirst) {
            gradient.orientation = orientationOne
            gradient.colors = intArrayOf(second, color, color, second)
        } else {
            gradient.orientation = orientationSecond
            gradient.colors = intArrayOf(color, second, color)
        }
        gradient.shape = GradientDrawable.RECTANGLE
        gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradient.cornerRadius = radius
        return gradient
    }

}

