package com.gkreduction.cardholder.ui.activity.camera


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Size
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityCameraBinding
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.dialog.settings.DialogSettings
import com.gkreduction.cardholder.utils.BarcodeAnalyzer
import com.gkreduction.domain.entity.ScanCode
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity :
    BaseActivity<CameraViewModel>(R.layout.activity_camera, CameraViewModel::class.java) {
    enum class TypeScan : Serializable {
        BASE,
        SECONDARY
    }

    var type: TypeScan = TypeScan.BASE
    private var cam: Camera? = null

    private lateinit var cameraExecutor: ExecutorService
    private var scanner: GmsBarcodeScanner? = null
    private var flashLightStatus = false

    companion object {
        private const val TAG = "CardHolder_Camera"
        private const val REQUEST_CODE_PERMISSIONS = 1
        private val REQUIRED_PERMISSIONS =
            mutableListOf(Manifest.permission.CAMERA)
                .toTypedArray()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            type = intent.extras!!.getSerializable(TYPE_SCAN) as TypeScan
        }
        scanner = GmsBarcodeScanning.getClient(this)
        scanCode()
        findViewById<ImageView?>(R.id.im_flash)
            .setOnClickListener { changeLighting(it as ImageView) }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                showDialogSettings()
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun scanCode() {
        if (scanner != null)
            scanner!!.startScan()
                .addOnSuccessListener { barcode ->
                    navigateToBack(createScanCode(barcode))
                }
                .addOnCanceledListener {
                    finish()
                }
                .addOnFailureListener {
                    initDefaultCamera()
                }

    }


    private fun initDefaultCamera() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    private fun createImageAnalyzer(): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setTargetResolution(
                Size(
                    (binding as ActivityCameraBinding).cameraPreview.width,
                    (binding as ActivityCameraBinding).cameraPreview.height
                )
            )
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    private fun createBarCodeAnalyser(imageAnalysis: ImageAnalysis): BarcodeAnalyzer {
        return BarcodeAnalyzer((binding as ActivityCameraBinding).viewClamp.getClampRect(),
            { navigateToBack(createScanCode(it)) },
            { if (it) imageAnalysis.clearAnalyzer() })

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = createImageAnalyzer()
            val analyzer = createBarCodeAnalyser(imageAnalysis)
            imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

            val preview = createPreview()
            try {
                cameraProvider.unbindAll()
                cam = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)

            }
        }, ContextCompat.getMainExecutor(this))


    }


    private fun createPreview(): Preview {
        return Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider((binding as ActivityCameraBinding).cameraPreview.surfaceProvider)
                val scanner = GmsBarcodeScanning.getClient(this)
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        navigateToBack(createScanCode(barcode))
                    }
            }
    }


    private fun changeLighting(view: ImageView) {
        flashLightStatus = !flashLightStatus
        if (cam?.cameraInfo?.hasFlashUnit() == true) {
            if (flashLightStatus)
                view.setImageResource(R.drawable.ic_flashlight_on)
            else
                view.setImageResource(R.drawable.ic_flashlight_off)
            cam?.cameraControl?.enableTorch(flashLightStatus)
        }
    }

    private fun createScanCode(barcode: Barcode): ScanCode {
        return ScanCode(
            type = (barcode.format),
            value = barcode.rawValue!!
        )
    }

    private fun navigateToBack(scanCode: ScanCode) {
        val returnIntent = Intent()
        returnIntent.putExtra(SCAN_CODE, scanCode)
        returnIntent.putExtra(TYPE_SCAN, type)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    private fun showDialogSettings() {
        val dialog = DialogSettings()
        dialog.setListener { isOk ->
            if (isOk) {
                openSettings()
            } else {
                dialog.dismiss()
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            }

        }
        dialog.show(supportFragmentManager, "")

    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + this.packageName)
        startActivity(intent)
    }

}
