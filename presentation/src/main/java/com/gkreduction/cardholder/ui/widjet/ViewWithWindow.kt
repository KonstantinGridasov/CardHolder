package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.gkreduction.cardholder.R


class ViewWithWindow(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var clampSize: Int = 100
    private var colorClamp = ContextCompat.getColor(context, R.color.background_camera)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var leftCl = 0f
    private var topCl = 0f
    private var rightCl = 0f
    private var bottomCl = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.clipOutRect(leftCl, topCl, rightCl, bottomCl)
        canvas.drawColor(colorClamp)

        paint.color = Color.RED
        paint.strokeWidth = 10f
        paint.style = Paint.Style.FILL

        canvas.drawLine(leftCl, topCl, leftCl + 100, topCl, paint)
        canvas.drawLine(leftCl, topCl, leftCl, topCl + 100, paint)

        paint.color = Color.GREEN
        canvas.drawLine(rightCl - 100, topCl, rightCl, topCl, paint)
        canvas.drawLine(rightCl, topCl + 100, rightCl, topCl, paint)


        paint.color = Color.BLUE
        canvas.drawLine(leftCl, bottomCl, leftCl + 100, bottomCl, paint)
        canvas.drawLine(leftCl, bottomCl, leftCl, bottomCl - 100, paint)


        paint.color = Color.YELLOW
        canvas.drawLine(rightCl - 100, bottomCl, rightCl, bottomCl, paint)
        canvas.drawLine(rightCl, bottomCl - 100, rightCl, bottomCl, paint)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        createClamp()
    }

    private fun createClamp() {
        clampSize = width / 6

        leftCl = width / 2f - clampSize * 2
        topCl = height / 2f - clampSize
        rightCl = width / 2f + clampSize * 2
        bottomCl = height / 2f + clampSize


        invalidate()
    }

    fun getClampRect(): Rect {
        return Rect(leftCl.toInt(), topCl.toInt(), rightCl.toInt(), bottomCl.toInt())
    }

}
