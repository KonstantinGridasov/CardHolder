package com.gkreduction.cardholder.ui.activity.main.fragment.card

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.usecase.GetCardByIdUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CardViewModel(
    context: Context,
    private var getCardByIdUseCase: GetCardByIdUseCase,
) :
    BaseAndroidViewModel(context.applicationContext as Application) {
    private var getCardById: Disposable? = null

    var card = ObservableField<Card>()
    var isSecondCode = MutableLiveData<Boolean>()

    fun getCards(id: Long) {
        if (getCardById != null)
            removeDisposable(getCardById!!)

        getCardById = getCardByIdUseCase
            .execute(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                card.set(it)
                if (it != null) {
                    isSecondCode.value = it.existSecondary
                }
            }

        addDisposable(getCardById!!)

    }

}