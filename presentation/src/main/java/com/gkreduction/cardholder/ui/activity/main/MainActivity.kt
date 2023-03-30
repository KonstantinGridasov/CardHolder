package com.gkreduction.cardholder.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.gkreduction.cardholder.ui.activity.add.AddActivity
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.card.CardActivity
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager


class MainActivity :
    BaseActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java),
    CardClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (binding as ActivityMainBinding).viewModel = viewModel
        initListeners()
    }


    override fun onResume() {
        super.onResume()
        viewModel.updateCards()
    }


    private fun initListeners() {
        (binding as ActivityMainBinding).toCamera.setOnClickListener { navigateToCameraActivity() }
        (binding as ActivityMainBinding).itemListener = this
    }


    private fun navigateToCameraActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    override fun onItemClick(id: Long) {
        Log.d("CARDHOLDER_APP", "Itemclick =${id}")
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra(CARD_ID, id)
        startActivity(intent)
    }
}

