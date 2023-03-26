package com.gkreduction.cardholder.widjet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class BarcodeView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val barcodeEncoder = BarcodeEncoder()

    private var barcode: Barcode? = null
    private val paint = Paint()

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.textSize = 40f
        paint.strokeWidth = 2f
        paint.textAlign = Paint.Align.CENTER
    }

    fun setBarcode(barcode: Barcode) {
        this.barcode = barcode
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (barcode != null) {
            canvas.save()
            canvas.translate(20f, 20f)
            try {
                val barcodeBitmap =
                    barcodeEncoder.encodeBitmap(
                        barcode!!.rawValue!!,
                        getBarcodeType(barcode!!.format),
                        barcode!!.boundingBox!!.width(),
                        barcode!!.boundingBox!!.height() - 50
                    )
                canvas.drawBitmap(barcodeBitmap, 0f, 0f, null)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
            barcode!!.rawValue?.let {
                canvas.drawText(
                    it,
                    barcode!!.boundingBox!!.width() / 2f,
                    barcode!!.boundingBox!!.height().toFloat(),
                    paint
                )
            }
            canvas.restore()
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


//public static final int FORMAT_ITF = 128;
//public static final int FORMAT_QR_CODE = 256;
//public static final int FORMAT_UPC_A = 512;
//public static final int FORMAT_UPC_E = 1024;
//public static final int FORMAT_PDF417 = 2048;
