package com.gkreduction.cardholder.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gkreduction.cardholder.utils.lazyThreadSafetyNone
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment<T : BaseAndroidViewModel>(
    var viewId: Int,
    var modelClass: Class<T>
) : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by lazyThreadSafetyNone {
        activity?.let {
            ViewModelProvider(it, viewModelFactory)[modelClass]
        }
    }

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, viewId, container, false)
        return binding.root
    }


}