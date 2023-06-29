package com.gkreduction.cardholder.ui.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.widjet.CustomToolbar
import com.gkreduction.domain.entity.ScanCode


class MainActivity :
    BaseActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java) {
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

//        setSupportActionBar((binding as ActivityMainBinding).toolbar)
//        (binding as ActivityMain2Binding).navView.setupWithNavController(navController)
//        (binding as ActivityMain2Binding).navView.setNavigationItemSelectedListener(this)

        initListener()
    }

    private fun initListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            activateToolbar(destination)
            setButtonText(destination)

        }
    }

    private fun activateToolbar(destination: NavDestination) {
        (binding as ActivityMainBinding).toolbar.setImageByDestination(destination.id)
    }


    private fun setButtonText(destination: NavDestination) {
        (binding as ActivityMainBinding).btOk.text
        val text = when (destination.id) {
            R.id.homeFragment -> resources.getString(R.string.add)
            R.id.infoFragment -> resources.getString(R.string.back)
            R.id.cardFragment -> resources.getString(R.string.revert)
            R.id.addFragment -> resources.getString(R.string.save)
            else -> ""
        }
        setVisibilityButton(text.isNotEmpty())
        (binding as ActivityMainBinding).btOk.text = text
    }

    fun setVisibilityButton(visible: Boolean) {
        (binding as ActivityMainBinding).btOk.visibility = if (visible)
            View.VISIBLE
        else
            View.GONE
    }

    fun getToolbar(): CustomToolbar {
        return (binding as ActivityMainBinding).toolbar
    }

    fun getButton(): AppCompatButton {
        return (binding as ActivityMainBinding).btOk
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

