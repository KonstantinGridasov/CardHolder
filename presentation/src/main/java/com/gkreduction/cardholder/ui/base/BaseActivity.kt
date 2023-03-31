package com.gkreduction.cardholder.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gkreduction.cardholder.tools.lazyThreadSafetyNone
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


open class BaseActivity<T : BaseAndroidViewModel>(var viewId: Int, var modelClass: Class<T>) :
    DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ViewDataBinding



    protected val viewModel by lazyThreadSafetyNone {
        ViewModelProvider(this, viewModelFactory)[modelClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, viewId)

    }

}