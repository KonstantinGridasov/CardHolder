package com.gkreduction.cardholder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanOptions




class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.toCamera.setOnClickListener { navigateToCameraActivity() }
    }

    private fun navigateToCameraActivity() {
        startActivity(Intent(this, CameraActivity::class.java))
    }
}

