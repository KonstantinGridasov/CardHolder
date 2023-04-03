package com.gkreduction.cardholder.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.utils.lazyThreadSafetyNone
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

open class BaseDialogFragment<T : BaseAndroidViewModel>(
    private var viewId: Int,
    private var modelClass: Class<T>
) :
    DaggerDialogFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ViewDataBinding

    protected val viewModel by lazyThreadSafetyNone {
        activity?.let {
            ViewModelProvider(it, viewModelFactory)[modelClass]
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mDialog = activity?.let { Dialog(it) }
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            viewId, null, false
        )
        mDialog?.setContentView(binding.root)
        mDialog.let { it ->
            it?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelOffset(R.dimen._200gkdp)
            )
        }
        initialized()
        return mDialog!!
    }

    open fun initialized() {}
}