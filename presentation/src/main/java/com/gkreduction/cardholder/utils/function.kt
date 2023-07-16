package com.gkreduction.cardholder.utils

import android.content.Context
import com.gkreduction.cardholder.R

fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun getDefaultCategoryName(context: Context) =
    context.resources.getString(R.string.default_category_name)