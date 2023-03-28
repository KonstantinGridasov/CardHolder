package com.gkreduction.cardholder.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.gkreduction.cardholder.ui.activity.add.AddActivity
import com.gkreduction.cardholder.ui.activity.base.BaseActivity


class MainActivity :
    BaseActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (binding as ActivityMainBinding).toCamera.setOnClickListener { navigateToCameraActivity() }
    }

    private fun navigateToCameraActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }
}

