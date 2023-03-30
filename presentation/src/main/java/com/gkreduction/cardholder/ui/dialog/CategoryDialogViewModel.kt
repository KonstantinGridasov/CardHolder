package com.gkreduction.cardholder.ui.dialog

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import com.gkreduction.cardholder.ui.base.BaseAndroidViewModel
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CategoryDialogViewModel(
    context: Context,
    private var getAllCategoryUseCase: GetAllCategoryUseCase,
    private var saveCategoryUseCase: SaveCategoryUseCase
) :
    BaseAndroidViewModel(context.applicationContext as Application) {

    private var getAllCategoryDis: Disposable? = null
    private var saveCategoryDis: Disposable? = null

    var list = ObservableField<List<Category>>()
    var chooses = ObservableField<Category>()

    var visibleAdd = ObservableField<Boolean>()

    fun revertVisible() {
        visibleAdd.set(!(visibleAdd.get() ?: false))
    }

    fun updateCategory(category: Category?) {
        if (category != null)
            chooses.set(category)
        else
            chooses.set(null)
    }


    fun getAllCategories() {
        if (getAllCategoryDis != null)
            removeDisposable(getAllCategoryDis!!)

        getAllCategoryDis = getAllCategoryUseCase
            .execute()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                visibleAdd.set(false)
                list.set(it)
            }

        addDisposable(getAllCategoryDis!!)

    }

    fun addCategory(string: String) {
        visibleAdd.set(false)
        if (saveCategoryDis != null)
            removeDisposable(getAllCategoryDis!!)

        getAllCategoryDis = saveCategoryUseCase
            .execute(string)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it)
                    getAllCategories()
            }

        addDisposable(getAllCategoryDis!!)
    }
}