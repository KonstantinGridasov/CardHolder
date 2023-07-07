package com.gkreduction.cardholder.ui.activity.main.fragment.add

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode
import com.gkreduction.domain.usecase.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddViewModel(
    context: Context,
    private var saveCardUseCase: SaveCardUseCase,
    private var getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase,
    private var getCardByIdUseCase: GetCardByIdUseCase,
    private var updateCardUseCase: UpdateCardUseCase,
    private var deleteCardUseCase: DeleteCardUseCase

) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var saveCardDis: Disposable? = null
    private var updateCardDis: Disposable? = null
    private var categoryByNameDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null
    private var getCardByIdDis: Disposable? = null
    private var deleteCardDis: Disposable? = null

    var card = ObservableField<Card>()

    fun getExistOrNewCard() = card.get() ?: getEmptyCard()


    fun updateColorStart(colorStart: Int) {
        val update = getExistOrNewCard()
        update.colorStart = colorStart
        card.set(update)
        card.notifyChange()
    }


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
                    this.card.set(getEmptyCard())
                }

            addDisposable(deleteCardDis!!)
        }

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

    fun updateCardName(string: Observable<String>) {
        observableSubscriber(string) {
            val update = getExistOrNewCard()
            update.cardName = it
            card.set(update)
            card.notifyChange()
        }

    }

    fun updateBaseInfo(string: Observable<String>) {
        observableSubscriber(string) {
            val update = getExistOrNewCard()
            update.cardBaseInfo = it
            card.set(update)
            card.notifyChange()
        }
    }

    fun updateSecondInfo(string: Observable<String>) {
        observableSubscriber(string) {
            val update = getExistOrNewCard()
            update.cardSecondInfo = it
            card.set(update)
            card.notifyChange()
        }
    }

    fun setBarcode(scanCode: ScanCode, type: CameraActivity.TypeScan) {
        val update = getExistOrNewCard()
        when (type) {
            CameraActivity.TypeScan.BASE -> update.primary = scanCode
            CameraActivity.TypeScan.SECONDARY -> update.secondary = scanCode
        }
        card.set(update)
        card.notifyChange()
    }


    private fun observableSubscriber(string: Observable<String>, item: (String) -> Unit) {
        string
            .doOnNext {
                item.invoke(it)
            }
            .subscribe()
    }


    fun getCardById(id: Long) {
        if (getCardByIdDis != null)
            removeDisposable(getCardByIdDis!!)

        getCardByIdDis = getCardByIdUseCase
            .execute(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                card.set(it)
            }

        addDisposable(getCardByIdDis!!)

    }

    fun updateCategory(category: Category?) {
        val update = getExistOrNewCard()
        if (category != null) {
            update.category = category
        } else
            getDefaultCategory()
        card.set(update)
        card.notifyChange()

    }

    fun getDefaultCategory() {
        val catName = getDefaultCategoryName(context)
        this.card.set(getEmptyCard())
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
                    setCategory(it)
            }

        addDisposable(categoryByNameDis!!)

    }

    private fun setCategory(category: Category) {
        val update = getExistOrNewCard()
        update.category = category
        card.set(update)
        card.notifyChange()
    }


    private fun createDefaultCategory() {
        if (saveCategoryDis != null)
            removeDisposable(saveCategoryDis!!)

        saveCategoryDis = saveCategoryUseCase
            .execute(getDefaultCategoryName(context))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                setCategory(it)
            }

        addDisposable(saveCategoryDis!!)
    }

    private fun getEmptyCard(): Card {
        val card = Card()
        card.colorStart = (Int.MIN_VALUE..Int.MAX_VALUE).random()
        card.colorEnd = (Int.MIN_VALUE..Int.MAX_VALUE).random()
        return card
    }
}