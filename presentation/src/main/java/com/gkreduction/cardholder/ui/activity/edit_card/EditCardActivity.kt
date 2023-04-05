package com.gkreduction.cardholder.ui.activity.edit_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.constant.CATEGORY
import com.gkreduction.cardholder.constant.SCAN_CODE
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityEditBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.category.CategoryActivity
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode


class EditCardActivity :
    BaseActivity<EditCardViewModel>(R.layout.activity_edit, EditCardViewModel::class.java) {
    private val startCameraForResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val scan = result.data!!.getSerializableExtra(SCAN_CODE) as ScanCode
                val type = result.data!!.getSerializableExtra(TYPE_SCAN) as CameraActivity.TypeScan
                viewModel.setBarcode(scan, type)
            }
        }

    private val startCategoryForResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val category = result.data!!.getSerializableExtra(CATEGORY) as Category
                viewModel.updateCategory(category)
            } else if (result.resultCode == Activity.RESULT_CANCELED)
                viewModel.updateCategory(null)
        }


    private enum class ModeEdit {
        CREATE, UPDATE
    }

    private var mode: ModeEdit = ModeEdit.CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()
        (binding as ActivityEditBinding).viewModel = viewModel

        if (intent.extras != null) {
            val cardId = intent.extras!!.getLong(CARD_ID)
            mode = ModeEdit.UPDATE
            if (cardId != 0L)
                viewModel.getCardById(cardId)
        } else
            viewModel.getCategoryByName(getDefaultCategoryName(this))
    }


    fun onClickBarcode(view: View?) {
        when (view?.id) {
            (binding as ActivityEditBinding).barcodeBase.id -> navigateToCameraActivity(
                CameraActivity.TypeScan.BASE
            )
            (binding as ActivityEditBinding).barcodeSecond.id -> navigateToCameraActivity(
                CameraActivity.TypeScan.SECONDARY
            )

        }
    }


    private fun initListener() {
        (binding as ActivityEditBinding).pickerFirst.listener = { viewModel.updateColorStart(it) }
        (binding as ActivityEditBinding).pickerSecond.listener = { viewModel.updateColorEnd(it) }
        (binding as ActivityEditBinding).save.setOnClickListener { saveCard() }
        (binding as ActivityEditBinding).llGroupCategory.setOnClickListener { navigateToCategory() }
    }

    private fun saveCard() {
        val card = viewModel.getExistOrNewCard()
        when (mode) {
            ModeEdit.CREATE -> viewModel.saveCard(card)
            ModeEdit.UPDATE -> viewModel.updateCard(card)
        }
        this.finish()
    }


    private fun navigateToCameraActivity(type: CameraActivity.TypeScan) {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra(TYPE_SCAN, type)
        startCameraForResult.launch(intent)
    }

    private fun navigateToCategory() {
        val intent = Intent(this, CategoryActivity::class.java)
        startCategoryForResult.launch(intent)
    }
}
