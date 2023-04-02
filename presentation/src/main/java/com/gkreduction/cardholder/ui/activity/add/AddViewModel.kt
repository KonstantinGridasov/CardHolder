package com.gkreduction.cardholder.ui.activity.add

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.usecase.GetCategoryByNameUseCase
import com.gkreduction.domain.usecase.SaveCardUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddViewModel(
    context: Context,
    private var saveCardUseCase: SaveCardUseCase,
    private var getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var saveCardDis: Disposable? = null
    private var categoryByNameDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null


    var categoryChoose = ObservableField<Category>()
    var categoryName = ObservableField<String>()

    fun saveCard(card: Card) {
        if (saveCardDis != null)
            removeDisposable(saveCardDis!!)

        saveCardDis = saveCardUseCase
            .execute(card)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { }

        addDisposable(saveCardDis!!)

    }

    fun updateCategory(category: Category?) {
        if (category != null) {
            categoryChoose.set(category)
            categoryName.set(category.catName)
        } else
            categoryName.set(getDefaultCategoryName(context))

    }

    fun getCategoryByName() {
        val catName: String = categoryName.get() ?: getDefaultCategoryName(context)
        if (categoryByNameDis != null)
            removeDisposable(categoryByNameDis!!)
        categoryByNameDis = getCategoryByNameUseCase
            .execute(catName)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .switchIfEmpty { createDefaultCategory() }
            .subscribe {
                if (it == null || it.catName.isEmpty()) {
                    createDefaultCategory()
                } else
                    categoryName.set(it.catName)
                categoryChoose.set(it)
            }

        addDisposable(categoryByNameDis!!)

    }


    private fun createDefaultCategory() {
        if (saveCategoryDis != null)
            removeDisposable(saveCategoryDis!!)

        saveCategoryDis = saveCategoryUseCase
            .execute(getDefaultCategoryName(context))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                categoryName.set(it.catName)
                categoryChoose.set(it)
            }

        addDisposable(saveCategoryDis!!)
    }

}