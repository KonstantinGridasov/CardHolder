package com.gkreduction.cardholder.ui.activity.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityAddBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.dialog.CategoryDialog
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
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

    private var colorStart: Int = 0
    private var colorEnd: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
        (binding as ActivityAddBinding).viewModel = viewModel
        viewModel.getCategoryByName()

    }


    fun onClickBarcode(view: View?) {
        when (view?.id) {
            (binding as ActivityAddBinding).barcodeBase.id, (binding as ActivityAddBinding).ivBarcode.id ->
                navigateToCameraActivity(CameraActivity.TypeScan.BASE)

            (binding as ActivityAddBinding).barcodeSecond.id, (binding as ActivityAddBinding).ivBarcodeSecond.id ->
                navigateToCameraActivity(CameraActivity.TypeScan.SECONDARY)

        }
    }


    fun showDialog(view: View?) {
        val dialog = CategoryDialog()
        dialog.setListener { viewModel.updateCategory(it) }
        dialog.show(supportFragmentManager, "")
    }

    private fun initListener() {
        (binding as ActivityAddBinding).pickerFirst.listener = { colorStart = it; changeColor() }
        (binding as ActivityAddBinding).pickerSecond.listener = { colorEnd = it; changeColor() }
        (binding as ActivityAddBinding).save.setOnClickListener { saveCard() }
    }

    private fun changeColor() {
        (binding as ActivityAddBinding).cardBase.changeColor(colorStart, colorEnd)
        (binding as ActivityAddBinding).cardCecondary.changeColor(colorStart, colorEnd)
    }


    private fun saveCard() {
        val category: Category = viewModel.categoryChoose.get() ?: Category(
            0L,
            getDefaultCategoryName(this)
        )

        val card = Card(
            category = category,
            colorStart = colorStart,
            colorEnd = colorEnd,
            cardName = (binding as ActivityAddBinding).editHeader.text.toString(),
            primary = (binding as ActivityAddBinding).barcodeBase.scanCode,
            secondary = (binding as ActivityAddBinding).barcodeSecond.scanCode,
            existSecondary = (binding as ActivityAddBinding).checkbox.isChecked
        )
        viewModel.saveCard(card)
        this.finish()

    }


    private fun setBarcode(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        when (type) {
            CameraActivity.TypeScan.BASE -> {
                (binding as ActivityAddBinding).barcodeBase.scanCode = (scanCode)
                (binding as ActivityAddBinding).ivBarcode.visibility = View.GONE
                (binding as ActivityAddBinding).barcodeBase.visibility = View.VISIBLE
            }
            CameraActivity.TypeScan.SECONDARY -> {
                (binding as ActivityAddBinding).barcodeSecond.scanCode = (scanCode)
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
