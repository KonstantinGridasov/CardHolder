package com.gkreduction.cardholder.ui.activity.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


open class BaseActivity<T : BaseAndroidViewModel>(var viewId: Int, var modelClass: Class<T>) :
    DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ViewDataBinding

    private fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE, initializer)

    protected val viewModel by lazyThreadSafetyNone {
        ViewModelProvider(this, viewModelFactory)[modelClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, viewId)

    }

}
