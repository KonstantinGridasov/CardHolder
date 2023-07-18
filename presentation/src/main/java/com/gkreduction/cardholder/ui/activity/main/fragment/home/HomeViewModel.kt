package com.gkreduction.cardholder.ui.activity.main.fragment.home

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.usecase.GetAllCardsUseCase
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import com.gkreduction.domain.usecase.GetCardByCategoryIdUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    context: Context,
    private var getAllCardsUseCase: GetAllCardsUseCase,
    private var getAllCategoryUseCase: GetAllCategoryUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase,
    private var getCardByCategoryIdUseCase: GetCardByCategoryIdUseCase,
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var getAllCards: Disposable? = null
    private var getAllCategoryDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null
    private var getCardByCategoryDis: Disposable? = null


    var listCards = ObservableField<List<Card>>()
    var listCategories = ObservableField<List<Category>>()
    var choosesCategory = ObservableField<Category>()


     fun getAllCategories() {
        if (getAllCategoryDis != null)
            removeDisposable(getAllCategoryDis!!)

        getAllCategoryDis = getAllCategoryUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    choosesCategory.set(it[0])
                    listCategories.set(it)
                    getAllCards()

                } else
                    createDefaultCategory()
            }

        addDisposable(getAllCategoryDis!!)

    }

    private fun createDefaultCategory() {
        if (saveCategoryDis != null)
            removeDisposable(saveCategoryDis!!)

        saveCategoryDis = saveCategoryUseCase
            .execute(getDefaultCategoryName(context))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                choosesCategory.set(it)
                updateCardByCategory(it)
            }

        addDisposable(saveCategoryDis!!)
    }

    fun updateCardByCategory(category: Category?) {
        if (category != null && category.position != 0) {
            getCardByCategoryId(category.catId)
        } else {
            getAllCards()
        }

    }

    private fun getCardByCategoryId(id: Long) {
        if (getCardByCategoryDis != null)
            removeDisposable(getCardByCategoryDis!!)

        getCardByCategoryDis = getCardByCategoryIdUseCase
            .execute(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listCards.set(it)
            }
        addDisposable(getCardByCategoryDis!!)
    }


    private fun getAllCards() {
        if (getAllCards != null)
            removeDisposable(getAllCards!!)

        getAllCards = getAllCardsUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listCards.set(it)
            }

        addDisposable(getAllCards!!)

    }


}