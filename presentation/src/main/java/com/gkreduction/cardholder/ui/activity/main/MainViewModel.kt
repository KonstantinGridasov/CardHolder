package com.gkreduction.cardholder.ui.activity.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.usecase.GetAllCardsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(context: Context, var getAllCardsUseCase: GetAllCardsUseCase) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var getAllCards: Disposable? = null

    var list = ObservableField<List<Card>>()

    fun updateCards() {
        if (getAllCards != null)
            removeDisposable(getAllCards!!)

        getAllCards = getAllCardsUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list.set(it)
            }

        addDisposable(getAllCards!!)

    }
}
