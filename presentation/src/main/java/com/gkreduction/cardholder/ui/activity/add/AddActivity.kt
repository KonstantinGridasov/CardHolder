package com.gkreduction.cardholder.ui.activity.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityAddBinding
import com.gkreduction.cardholder.ui.activity.base.BaseActivity
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.widjet.CVColorPicker
import com.gkreduction.domain.entity.ScanCode


class AddActivity : BaseActivity<AddViewModel>(R.layout.activity_add, AddViewModel::class.java) {

    private val startForResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val scan = result.data!!.getSerializableExtra(SCAN_CODE) as ScanCode
                val type = result.data!!.getSerializableExtra(TYPE_SCAN) as CameraActivity.TypeScan
                setBarcode(scan, type)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()

    }


    fun onClickBarcode(view: View?) {
        when (view?.id) {
            (binding as ActivityAddBinding).barcodeBase.id, (binding as ActivityAddBinding).ivBarcode.id ->
                navigateToCameraActivity(CameraActivity.TypeScan.BASE)

            (binding as ActivityAddBinding).barcodeSecond.id, (binding as ActivityAddBinding).ivBarcodeSecond.id ->
                navigateToCameraActivity(CameraActivity.TypeScan.SECONDARY)

        }
    }


    private fun initListener() {
        (binding as ActivityAddBinding).cvColorPicker.listener =
            object : CVColorPicker.OnChangeColorListener {
                override fun onChangeColor(color: Int) {
                    changeColor(color)
                }
            }
    }

    private fun changeColor(changeColor: Int) {
        (binding as ActivityAddBinding).cardBase.setCardBackgroundColor(changeColor)
        (binding as ActivityAddBinding).cardCecondary.setCardBackgroundColor(changeColor)

    }

    private fun setBarcode(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        when (type) {
            CameraActivity.TypeScan.BASE -> {
                (binding as ActivityAddBinding).barcodeBase.setScanCode(scanCode)
                (binding as ActivityAddBinding).ivBarcode.visibility = View.GONE
                (binding as ActivityAddBinding).barcodeBase.visibility = View.VISIBLE
            }
            CameraActivity.TypeScan.SECONDARY -> {
                (binding as ActivityAddBinding).barcodeSecond.setScanCode(scanCode)
                (binding as ActivityAddBinding).ivBarcodeSecond.visibility = View.GONE
                (binding as ActivityAddBinding).barcodeSecond.visibility = View.VISIBLE
            }
        }

    }


    private fun navigateToCameraActivity(type: CameraActivity.TypeScan) {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra(TYPE_SCAN, type)
        startForResult.launch(intent)
    }
}
