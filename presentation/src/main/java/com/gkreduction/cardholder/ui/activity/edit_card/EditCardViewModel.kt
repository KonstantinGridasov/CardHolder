package com.gkreduction.cardholder.ui.activity.edit_card

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode
import com.gkreduction.domain.usecase.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditCardViewModel(
    context: Context,
    private var saveCardUseCase: SaveCardUseCase,
    private var getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase,
    private var getCardByIdUseCase: GetCardByIdUseCase,
    private var updateCardUseCase: UpdateCardUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var saveCardDis: Disposable? = null
    private var updateCardDis: Disposable? = null
    private var categoryByNameDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null
    private var getCardById: Disposable? = null

    var card = ObservableField<Card>()
    var primary = ObservableField<ScanCode>()
    var secondary = ObservableField<ScanCode>()
    var existSecondary = ObservableField<Boolean>()

    var categoryChoose = ObservableField<Category>()
    var categoryName = ObservableField<String>()

    fun createCard(
        header: String,
        cardBaseInfo: String,
        cardSecondInfo: String,
        colorStart: Int,
        colorEnd: Int
    ) = Card(
        cardId = card.get()?.cardId ?: 0L,
        category = getCategory(),
        primary = primary.get() ?: ScanCode(),
        secondary = secondary.get() ?: ScanCode(),
        existSecondary = (secondary.get() != null),
        colorEnd = colorStart,
        colorStart = colorEnd,
        cardBaseInfo = cardBaseInfo,
        cardName = header,
        cardSecondInfo = cardSecondInfo
    )

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

    fun updateCard(card: Card) {
        if (updateCardDis != null)
            removeDisposable(updateCardDis!!)

        updateCardDis = updateCardUseCase
            .execute(card)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { }
        addDisposable(updateCardDis!!)
    }

    private fun getCategory(): Category {
        return if (categoryChoose.get() != null)
            categoryChoose.get()!!
        else {
            Category(catName = categoryName.get() ?: "")

        }
    }

    fun getCardById(id: Long) {
        if (getCardById != null)
            removeDisposable(getCardById!!)

        getCardById = getCardByIdUseCase
            .execute(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                card.set(it)
                primary.set(it?.primary)
                secondary.set(it?.secondary)
                existSecondary.set(it?.existSecondary)
                getCategoryByName(it?.category?.catName)
            }

        addDisposable(getCardById!!)

    }

    fun updateCategory(category: Category?) {
        if (category != null) {
            categoryChoose.set(category)
            categoryName.set(category.catName)
        } else
            categoryName.set(getDefaultCategoryName(context))

    }

    fun getCategoryByName(name: String?) {
        val catName: String = name ?: getDefaultCategoryName(context)
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