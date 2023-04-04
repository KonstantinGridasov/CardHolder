package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.gkreduction.cardholder.R


class ViewWithWindow(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var clampSize: Int = 100
    private var colorClamp = ContextCompat.getColor(context, R.color.background_camera)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipOutRect(
            width / 2f - clampSize * 2,
            height / 2f - clampSize,
            width / 2f + clampSize * 2,
            height / 2f + clampSize
        )

        canvas.drawColor(colorClamp)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        createClamp()
    }

    private fun createClamp() {
        clampSize = width / 6
        invalidate()
    }

    fun getClampRect(): Rect {
        return Rect(
            width / 2 - clampSize * 2,
            height / 2 - clampSize,
            width / 2 + clampSize * 2,
            height / 2 + clampSize
        )
    }

}
