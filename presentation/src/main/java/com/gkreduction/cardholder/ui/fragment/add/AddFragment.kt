package com.gkreduction.cardholder.ui.fragment.add

import android.view.View
import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentAddBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.domain.entity.ScanCode

class AddFragment : BaseFragment<AddViewModel>(
    R.layout.fragment_add,
    AddViewModel::class.java
), MainActivity.CameraResultListener, OnBarcodeListener {

    private enum class ModeEdit {
        CREATE, UPDATE
    }

    private var mode: ModeEdit = ModeEdit.CREATE


    override fun onStart() {
        super.onStart()
        (binding as FragmentAddBinding).viewModel = viewModel

        val args = AddFragmentArgs.fromBundle(requireArguments())
        if (args.cardId != 0L) {
            mode = ModeEdit.UPDATE
            viewModel?.getCardById(args.cardId)

        } else if (args.category != null) {
            viewModel?.updateCategory(args.category)
        } else
            viewModel?.getCategoryByName()
        updateToolbarName()
        initListener()
    }

    private fun updateToolbarName() {
        if (activity is MainActivity) {
            (activity as MainActivity).updateToolbarName(context?.resources?.getString(R.string.text_editor_toolbar))

        }
    }

    override fun onClickBarcode(view: View?) {
        when (view?.id) {
            (binding as FragmentAddBinding).barcodeBase.id -> navigateToCameraActivity(
                CameraActivity.TypeScan.BASE
            )
            (binding as FragmentAddBinding).barcodeSecond.id -> navigateToCameraActivity(
                CameraActivity.TypeScan.SECONDARY
            )

        }
    }


    private fun initListener() {
        (binding as FragmentAddBinding).listener = this
        (binding as FragmentAddBinding).pickerFirst.listener = { viewModel?.updateColorStart(it) }
        (binding as FragmentAddBinding).pickerSecond.listener = { viewModel?.updateColorEnd(it) }
        (binding as FragmentAddBinding).llGroupCategory.setOnClickListener { navigateToCategory() }

        if (activity is MainActivity) {
            (activity as MainActivity).setListenerCamera(this)
        }
        (binding as FragmentAddBinding).llGroupSave.setOnClickListener { saveCard() }
    }

    private fun saveCard() {
        if (viewModel != null) {
            val card = viewModel!!.getExistOrNewCard()
            when (mode) {
                ModeEdit.CREATE -> viewModel?.saveCard(card)
                ModeEdit.UPDATE -> viewModel?.updateCard(card)
            }
        }
        view?.findNavController()?.navigateUp()

    }


    private fun navigateToCameraActivity(type: CameraActivity.TypeScan) {
        if (activity is MainActivity) {
            (activity as MainActivity).navigateToCameraActivity(type)
        }
    }

    private fun navigateToCategory() {
        view?.findNavController()?.navigate(R.id.categoryFragment)
    }

    override fun onResult(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        viewModel?.setBarcode(scanCode, type)
    }
}