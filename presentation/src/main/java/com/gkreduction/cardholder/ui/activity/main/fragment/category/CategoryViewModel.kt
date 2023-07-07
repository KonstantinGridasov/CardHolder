package com.gkreduction.cardholder.ui.activity.main.fragment.category

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.usecase.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(
    context: Context,
    private var getAllCategoryUseCase: GetAllCategoryUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase,
    private var updateCategoryUseCase: UpdateCategoryUseCase,
    private var deleteCategoryUseCase: DeleteCategoryUseCase,
    private var updatePosition: UpdatePositionCategoryUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var getAllCategoryDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null
    private var updateCategoryDis: Disposable? = null
    private var deleteCategoryDis: Disposable? = null
    private var updatePositionDis: Disposable? = null

    private var allCategories = ArrayList<Category>()
    var list = ObservableField<List<Category>>()
    var chooses = ObservableField<Category>()


    fun addNewCategory() {
        allCategories.add(Category())
        list.set(allCategories)
        list.notifyChange()
    }


    fun getAllCategories() {
        if (getAllCategoryDis != null)
            removeDisposable(getAllCategoryDis!!)

        getAllCategoryDis = getAllCategoryUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                allCategories = ArrayList(it)
                list.set(allCategories)
            }

        addDisposable(getAllCategoryDis!!)

    }

    fun saveCategory(string: String) {
        if (saveCategoryDis != null)
            removeDisposable(saveCategoryDis!!)

        saveCategoryDis = saveCategoryUseCase
            .execute(string)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getAllCategories()
            }

        addDisposable(saveCategoryDis!!)
    }

    fun removeCategory(it: Category) {
        if (deleteCategoryDis != null)
            removeDisposable(deleteCategoryDis!!)

        deleteCategoryDis = deleteCategoryUseCase
            .execute(it.catId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getAllCategories()
            }

        addDisposable(deleteCategoryDis!!)

    }

    fun updateCategory(it: Category) {
        if (updateCategoryDis != null)
            removeDisposable(updateCategoryDis!!)

        updateCategoryDis = updateCategoryUseCase
            .execute(it)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getAllCategories()
            }

        addDisposable(updateCategoryDis!!)

    }

    fun updatePosition(list: List<Category>) {
        if (updatePositionDis != null)
            removeDisposable(updatePositionDis!!)

        updatePositionDis = updatePosition
            .execute(list)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getAllCategories()
            }

        addDisposable(updatePositionDis!!)
    }
}