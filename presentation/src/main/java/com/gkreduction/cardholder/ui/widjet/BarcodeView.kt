package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.gkreduction.cardholder.R
import com.gkreduction.domain.entity.ScanCode

import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder


class BarcodeView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private val barcodeEncoder = BarcodeEncoder()
    private val paint = Paint()

    var scanCode: ScanCode = ScanCode()
        set(value) {
            field = value
            requestLayout()
        }


    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.textSize = resources.getDimension(R.dimen._10gkdp)
        paint.strokeWidth = 2f
        paint.textAlign = Paint.Align.CENTER
    }


    private fun updateBitmap() {
        setImageBitmap(createBarCode())

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateBitmap()
    }

    private fun createBarCode(): Bitmap {
        val min = width.coerceAtLeast(height)
        val max = width.coerceAtMost(height)
        val empty = Bitmap.createBitmap(min + 10, max + 10, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(empty)
        canvas.drawColor(Color.WHITE)
        try {
            val barcodeBitmap = barcodeEncoder.encodeBitmap(
                scanCode.value, getBarcodeType(scanCode.type), min, max - 80
            )
            canvas.drawBitmap(barcodeBitmap, 10f, 10f, null)
            canvas.drawText(scanCode.value, min / (2f), max - paint.textSize + 25f, paint)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return empty
    }


    private fun getBarcodeType(int: Int): BarcodeFormat {
        return when (int) {
            1 -> BarcodeFormat.CODE_128
            2 -> BarcodeFormat.CODE_39
            4 -> BarcodeFormat.CODE_93
            8 -> BarcodeFormat.CODABAR
            16 -> BarcodeFormat.DATA_MATRIX
            32 -> BarcodeFormat.EAN_13
            64 -> BarcodeFormat.EAN_8
            128 -> BarcodeFormat.ITF
            256 -> BarcodeFormat.QR_CODE
            512 -> BarcodeFormat.UPC_A
            1024 -> BarcodeFormat.UPC_E
            2048 -> BarcodeFormat.PDF_417
            4096 -> BarcodeFormat.AZTEC
            else -> BarcodeFormat.EAN_13
        }
    }


}

