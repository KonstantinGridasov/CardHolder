package com.gkreduction.cardholder.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import com.gkreduction.cardholder.R
import java.io.Serializable

fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun getDefaultCategoryName(context: Context) =
    context.resources.getString(R.string.default_category_name)