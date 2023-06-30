package com.gkreduction.cardholder.ui.activity.splash

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.usecase.GetThemeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SplashScreenViewModel(
    context: Context,
    private val getThemeUseCase: GetThemeUseCase,
) : BaseAndroidViewModel(context.applicationContext as Application) {
    private var getThemeDis: Disposable? = null

    var isThemeSet = MutableLiveData<Boolean>()


    fun getTheme() {
        if (getThemeDis != null)
            removeDisposable(getThemeDis!!)

        getThemeDis = getThemeUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { setTheme(it) }
            .subscribe()

        addDisposable(getThemeDis!!)

    }

    private fun setTheme(it: Int?) {
        if (it != null && it != 0)
            AppCompatDelegate.setDefaultNightMode(it)
        isThemeSet.value = true
    }

}