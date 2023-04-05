package com.gkreduction.cardholder.ui.dialog

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel

class DialogInfoViewModel(
    context: Context,
) :
    BaseAndroidViewModel(context.applicationContext as Application) {
    var infoText = ObservableField<String>()

    fun updateInfo(info: String) {
        infoText.set(info)
        infoText.notifyChange()
    }

}