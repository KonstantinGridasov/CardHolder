package com.gkreduction.cardholder.ui.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.adapter.CategoryClickListener
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode
import com.google.android.material.navigation.NavigationView


class MainActivity :
    BaseActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java),
    CategoryClickListener, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController

    interface CameraResultListener {
        fun onResult(scanCode: ScanCode, type: CameraActivity.TypeScan)
    }

    private var listenerCam: CameraResultListener? = null
    private val startCameraForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val scan = result.data!!.getSerializableExtra(SCAN_CODE) as ScanCode
                val type = result.data!!.getSerializableExtra(TYPE_SCAN) as CameraActivity.TypeScan
                listenerCam?.onResult(scan, type)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = this.findNavController(R.id.fcNavigation)
        (binding as ActivityMainBinding).viewModel = viewModel

        setSupportActionBar((binding as ActivityMainBinding).toolbar)
        (binding as ActivityMainBinding).navView.setupWithNavController(navController)
        (binding as ActivityMainBinding).navView.setNavigationItemSelectedListener(this)

        initListener()
    }

    private fun initListener() {
        (binding as ActivityMainBinding).imageToolbar.setOnClickListener {
            (binding as ActivityMainBinding).drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    private fun navigateToCategory() {
        navController.navigate(R.id.categoryFragment)
    }

    private fun navigateToAdd() {
        navController.navigate(R.id.addFragment)
    }

    private fun navigateToHome() {
        navController.navigate(R.id.homeFragment)

    }


    override fun onItemClick(category: Category?) {
        viewModel.sortListByCategory(category)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> navigateToHome()
            R.id.nav_add -> navigateToAdd()
            R.id.nav_category -> navigateToCategory()
        }
        (binding as ActivityMainBinding).drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    fun updateToolbarName(name: String?) {
        viewModel.updateToolbarName(name)
    }


    fun setListenerCamera(listener: CameraResultListener) {
        this.listenerCam = listener
    }

    fun navigateToCameraActivity(type: CameraActivity.TypeScan) {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra(TYPE_SCAN, type)
        startCameraForResult.launch(intent)
    }
}

