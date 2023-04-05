package com.gkreduction.cardholder.ui.activity.card

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.usecase.DeleteCardUseCase
import com.gkreduction.domain.usecase.GetCardByIdUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CardViewModel(
    context: Context,
    private var getCardByIdUseCase: GetCardByIdUseCase,
    private var deleteCardUseCase: DeleteCardUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {
    private var getCardById: Disposable? = null
    private var deleteCardDis: Disposable? = null

    var card = ObservableField<Card>()

    fun getCards(id: Long) {
        if (getCardById != null)
            removeDisposable(getCardById!!)

        getCardById = getCardByIdUseCase
            .execute(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                card.set(it)
            }

        addDisposable(getCardById!!)

    }

    fun deleteCard() {
        val card = card.get()
        if (card != null) {
            if (deleteCardDis != null)
                removeDisposable(deleteCardDis!!)

            deleteCardDis = deleteCardUseCase
                .execute(card)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }

            addDisposable(deleteCardDis!!)
        }

    }
}