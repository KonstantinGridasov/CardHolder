package com.gkreduction.cardholder.ui.activity.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

@Suppress("ConvertSecondaryConstructorToPrimary")
@SuppressLint("StaticFieldLeak")
abstract class BaseAndroidViewModel : AndroidViewModel {
    constructor(application: Application) : super(application) {
        this.context = application
    }

    protected val context: Context


}