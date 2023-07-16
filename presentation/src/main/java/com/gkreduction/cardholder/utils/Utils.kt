package com.gkreduction.cardholder.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.gkreduction.cardholder.R


fun getColorByStatus(selected: Boolean, view: View): Int {
    return if (selected) view.context.getColorFromAttr(R.attr.colorOnPrimary)
    else view.context.getColorFromAttr(R.attr.colorOnSecondary)
}


@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

