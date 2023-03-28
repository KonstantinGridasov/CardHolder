package com.gkreduction.cardholder.ui.widjet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.gkreduction.domain.entity.ScanCode
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class BarcodeView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val barcodeEncoder = BarcodeEncoder()

    private var scanCode: ScanCode? = null
    private val paint = Paint()

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.textSize = 30f
        paint.strokeWidth = 2f
        paint.textAlign = Paint.Align.CENTER
    }

    fun setScanCode(scanCode: ScanCode) {
        this.scanCode = scanCode
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (scanCode != null) {
            try {
                val barcodeBitmap =
                    barcodeEncoder.encodeBitmap(
                        scanCode!!.value,
                        getBarcodeType(scanCode!!.type),
                        width, height - 60
                    )
                canvas.drawBitmap(barcodeBitmap, 0f, 10f, null)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

            scanCode!!.value.let {
                canvas.drawText(
                    it, width / 2f, height.toFloat() - 10, paint
                )
            }
        }

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

