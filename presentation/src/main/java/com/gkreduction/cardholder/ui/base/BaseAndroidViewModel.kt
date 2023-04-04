package com.gkreduction.cardholder.ui.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.domain.entity.ScanCode
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("ConvertSecondaryConstructorToPrimary")
@SuppressLint("StaticFieldLeak")
abstract class BaseAndroidViewModel : AndroidViewModel {
    constructor(application: Application) : super(application) {
        this.context = application
    }

    protected val context: Context


    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
    }


    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        disposables.remove(disposable)
    }



}