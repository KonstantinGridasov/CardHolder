package com.gkreduction.cardholder.ui.activity.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ActivityAddBinding
import com.gkreduction.cardholder.ui.activity.base.BaseActivity
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.widjet.CVColorPicker
import com.gkreduction.domain.entity.ScanCode


class AddActivity : BaseActivity<AddViewModel>(R.layout.activity_add, AddViewModel::class.java) {

    private val startForResult = registerForActivityResult(StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scan = result.data!!.getSerializableExtra("result") as ScanCode
            setBarcode(scan)
            // Handle the Intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
    }

    private fun initListener() {

        (binding as ActivityAddBinding).cvColorPicker.listener =
            object : CVColorPicker.OnChangeColorListener {
                override fun onChangeColor(color: Int) {
                    changeColor(color)
                }
            }

//        (binding as ActivityAddBinding).ivBarcode.setOnClickListener { navigateToCameraActivity() }
//        (binding as ActivityAddBinding).barcodeBase.setOnClickListener { navigateToCameraActivity() }
    }


    fun changeColor(changeColor: Int) {
        (binding as ActivityAddBinding).cardBase.setCardBackgroundColor(changeColor)

    }

    private fun setBarcode(scanCode: ScanCode) {
        (binding as ActivityAddBinding).barcodeBase.setScanCode(scanCode)
        (binding as ActivityAddBinding).ivBarcode.visibility = View.GONE
        (binding as ActivityAddBinding).barcodeBase.visibility = View.VISIBLE


    }

    fun navigateToCameraActivity(view: View?) {
        startForResult.launch(Intent(this, CameraActivity::class.java))
    }
}
