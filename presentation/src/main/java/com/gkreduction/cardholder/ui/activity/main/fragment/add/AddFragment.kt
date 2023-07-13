package com.gkreduction.cardholder.ui.activity.main.fragment.add

import android.view.View
import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentAddBinding
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.base.BaseFragment
import com.gkreduction.cardholder.ui.dialog.preview.DialogPreview
import com.gkreduction.cardholder.ui.dialog.remove.DialogRemove
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
            viewModel?.getDefaultCategory()
        initListener()
    }

    override fun onResult(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        showDialogPreview(scanCode, type)
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
        (binding as FragmentAddBinding).picker.listener = { setColor(it) }
        (binding as FragmentAddBinding).tvCategory.setOnClickListener { navigateToCategory() }
        if (activity is MainActivity) {
            (activity as MainActivity).setListenerCamera(this)
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar().setOnImageClickListener { showDialogDelete() }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).getButton().setOnClickListener { saveCard() }
        }
    }

    private fun setColor(color: Int) {
        (binding as FragmentAddBinding).llItemColor.setBackgroundColor(color)
        viewModel?.updateColorStart(color)
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

    private fun showDialogDelete() {
        val info = viewModel?.card?.get()?.cardName ?: ""
        if (activity is MainActivity)
            (activity as MainActivity).let {
                val dialog = DialogRemove()
                dialog.setListener(info, true) { isDell ->
                    if (isDell) {
                        viewModel?.deleteCard()
                        navigateUp()
                    }
                }
                dialog.show(it.supportFragmentManager, "")
            }

    }

    private fun showDialogPreview(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        if (activity is MainActivity)
            (activity as MainActivity).let {
                val dialog = DialogPreview()
                dialog.setListener(scanCode) { isOk ->
                    if (isOk) {
                        viewModel?.setBarcode(scanCode, type)
                    } else {
                        dialog.dismiss()
                        navigateToCameraActivity(type)
                    }

                }
                dialog.show(it.supportFragmentManager, "")
            }
    }

    private fun navigateUp() {
        view?.findNavController()?.navigate(AddFragmentDirections.addToHome())
    }

    private fun navigateToCameraActivity(type: CameraActivity.TypeScan) {
        if (activity is MainActivity) {
            (activity as MainActivity).navigateToCameraActivity(type)
        }
    }

    private fun navigateToCategory() {
        view?.findNavController()?.navigate(AddFragmentDirections.addToCategory())
    }


}