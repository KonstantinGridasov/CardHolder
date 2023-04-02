package com.gkreduction.cardholder.ui.activity.main

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.usecase.GetAllCardsUseCase
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    context: Context,
    private var getAllCardsUseCase: GetAllCardsUseCase,
    private var getAllCategoryUseCase: GetAllCategoryUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var getAllCards: Disposable? = null
    private var getAllCategoryDis: Disposable? = null

    var list = ObservableField<List<Card>>()
    var categories = ObservableField<List<Category>>()
    var choosesCategory = ObservableField<Category>()


    fun getAllCategories() {
        if (getAllCategoryDis != null)
            removeDisposable(getAllCategoryDis!!)

        getAllCategoryDis = getAllCategoryUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                categories.set(it)
                updateCards()
            }

        addDisposable(getAllCategoryDis!!)

    }

    private fun updateCards() {
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
