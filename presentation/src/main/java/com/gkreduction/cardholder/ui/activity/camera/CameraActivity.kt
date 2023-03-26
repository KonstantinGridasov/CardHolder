package com.gkreduction.cardholder.ui.activity.camera


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ActivityCameraBinding
import com.gkreduction.cardholder.ui.activity.base.BaseActivity
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.utils.BarcodeAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity :
    BaseActivity<CameraViewModel>(R.layout.activity_camera, CameraViewModel::class.java) {
    private var barcodeView: BarcodeView? = null

    companion object {
        private const val TAG = "CardHolder_Camera"
        private const val REQUEST_CODE_PERMISSIONS = 1
        private val REQUIRED_PERMISSIONS =
            mutableListOf(Manifest.permission.CAMERA)
                .toTypedArray()
    }


    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
        barcodeView = (binding as ActivityCameraBinding).barcode
        (binding as ActivityCameraBinding).rescan.setOnClickListener { startCamera() }
    }


    private fun createPreview(): Preview {
        return Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider((binding as ActivityCameraBinding).cameraPreview.surfaceProvider)
            }

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
        return BarcodeAnalyzer(
            { barcodeView?.setBarcode(it) },
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
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))


    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
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
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


}
