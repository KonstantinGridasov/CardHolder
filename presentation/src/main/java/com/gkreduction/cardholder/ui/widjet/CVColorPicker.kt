package com.gkreduction.cardholder.ui.widjet

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CVColorPicker(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mBitmap: Bitmap? = null
    var listener: ((Int) -> Unit)? = null

    private val colors = intArrayOf(
        Color.BLACK,/*ORANGE*/
        Color.parseColor("#9400D3"),/*violet*/
        Color.parseColor("#00008B"),/*dark BLUE*/
        Color.parseColor("#00BFFF"),/*BLUE*/
        Color.parseColor("#255F00"), /*GREEN*/
        Color.parseColor("#FFFA0E"),/*YELLOW*/
        Color.parseColor("#FF8C00"),/*ORANGE*/
        Color.parseColor("#C41E3A"), /*RED*/
    )


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, null)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initShader()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(ev.x, ev.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(ev.x, ev.y)
                return true
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun initShader() {
        val shader = LinearGradient(
            width.toFloat(), 0f, 0f, 0f,
            colors, null, Shader.TileMode.CLAMP
        )
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        val canvas = Canvas(mBitmap!!)
        val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.shader = shader
        mPaint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
    }


    private fun touchUp() {}
    private fun touchMove(mX: Float, mY: Float) {
        if (mX > 0 && mX < mBitmap!!.width && mY > 0 && mY < mBitmap!!.height) {
            val color = mBitmap!!.getPixel(mX.toInt(), mY.toInt())
            listener?.invoke(color)

            invalidate()
        }
    }

    private fun touchStart(mX: Float, mY: Float) {
        if (mX > 0 && mX < mBitmap!!.width && mY > 0 && mY < mBitmap!!.height) {
            val color = mBitmap!!.getPixel(mX.toInt(), mY.toInt())
            listener?.invoke(color)
            invalidate()
        }
    }
}