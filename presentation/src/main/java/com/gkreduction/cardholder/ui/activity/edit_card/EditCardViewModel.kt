package com.gkreduction.cardholder.ui.activity.edit_card

import android.app.Application
import android.content.Context
import android.util.Log
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
import io.reactivex.internal.disposables.DisposableHelper.dispose
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
    private var getCardByIdDis: Disposable? = null

    var card = ObservableField<Card>()

    fun getExistOrNewCard() = card.get() ?: Card()

    fun updateExist(isChecked: Boolean) {
        val update = getExistOrNewCard()
        update.existSecondary = isChecked
        card.set(update)
        card.notifyChange()
    }

    fun updateColorStart(colorStart: Int) {
        val update = getExistOrNewCard()
        update.colorStart = colorStart
        card.set(update)
        card.notifyChange()
    }

    fun updateColorEnd(colorEnd: Int) {
        val update = getExistOrNewCard()
        update.colorEnd = colorEnd
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

    fun updateHeader(string: Observable<String>) {
        observableSubscriber(string) {
            val update = getExistOrNewCard()
            update.cardName = it
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
            getCategoryByName(null)
        card.set(update)
        card.notifyChange()

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

}