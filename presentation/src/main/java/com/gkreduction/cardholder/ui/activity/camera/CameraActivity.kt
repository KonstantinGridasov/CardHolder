package com.gkreduction.cardholder.ui.activity.camera


import android.content.Intent
import android.os.Bundle
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.domain.entity.ScanCode
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import java.io.Serializable


class CameraActivity :
    BaseActivity<CameraViewModel>(R.layout.activity_camera, CameraViewModel::class.java) {
    enum class TypeScan : Serializable {
        BASE,
        SECONDARY
    }

    var type: TypeScan = TypeScan.BASE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            type = intent.extras!!.getSerializable(TYPE_SCAN) as TypeScan
        }

        val scanner = GmsBarcodeScanning.getClient(this)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                navigateToBack(createScanCode(barcode))
            }
            .addOnCanceledListener {
                finish()
            }
            .addOnFailureListener {
                finish()
            }
    }


    private fun navigateToBack(scanCode: ScanCode) {
        val returnIntent = Intent()
        returnIntent.putExtra(SCAN_CODE, scanCode)
        returnIntent.putExtra(TYPE_SCAN, type)
        setResult(RESULT_OK, returnIntent)
        finish()

    }


    private fun createScanCode(barcode: Barcode): ScanCode {
        return ScanCode(
            type = (barcode.format),
            value = barcode.rawValue!!
        )

    }
}
