package com.gkreduction.cardholder.ui.activity.main.fragment.info

import android.app.Application
import android.content.Context
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.usecase.SaveThemeUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InfoViewModel(
    context: Context,
    private var saveThemeUseCase: SaveThemeUseCase,
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var saveTheme: Disposable? = null

    fun saveTheme(mode: Int) {
        if (saveTheme != null)
            removeDisposable(saveTheme!!)

        saveTheme = saveThemeUseCase
            .execute(mode)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { }
            .subscribe()

        addDisposable(saveTheme!!)
    }

}