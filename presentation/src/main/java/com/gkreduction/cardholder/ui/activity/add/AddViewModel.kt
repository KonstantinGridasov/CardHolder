package com.gkreduction.cardholder.ui.activity.add

import android.app.Application
import android.content.Context
import android.util.Log
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.usecase.SaveCardUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddViewModel(context: Context, var saveCardUseCase: SaveCardUseCase) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var save: Disposable? = null

    fun saveCard(card: Card) {
        if (save != null)
            removeDisposable(save!!)

        save = saveCardUseCase
            .execute(card)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {  }

        addDisposable(save!!)

    }
}