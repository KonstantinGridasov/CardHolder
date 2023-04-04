package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.CategoryScope
import com.gkreduction.cardholder.ui.activity.category.CategoryViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.DeleteCategoryUseCase
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import com.gkreduction.domain.usecase.UpdateCategoryUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class CategoryModule {
    companion object {
        @Provides
        @CategoryScope
        fun providesUpdateCategoryUseCase(service: DbServiceImpl) = UpdateCategoryUseCase(service)

        @Provides
        @CategoryScope
        fun providesDeleteCategoryUseCase(service: DbServiceImpl) = DeleteCategoryUseCase(service)


        @Provides
        fun provideCardModule(
            app: Application,
            getAllCategoryUseCase: GetAllCategoryUseCase,
            saveCategoryUseCase: SaveCategoryUseCase,
            updateCategoryUseCase: UpdateCategoryUseCase,
            deleteCategoryUseCase: DeleteCategoryUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                            CategoryViewModel(
                                app,
                                getAllCategoryUseCase,
                                saveCategoryUseCase,
                                updateCategoryUseCase,
                                deleteCategoryUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}