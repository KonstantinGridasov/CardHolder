package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.DialogScope
import com.gkreduction.cardholder.ui.dialog.CategoryDialogViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class DialogModule {
    companion object {
        @Provides
        @DialogScope
        fun providesGetAllCategoryUseCase(service: DbServiceImpl) = GetAllCategoryUseCase(service)

        @Provides
        @DialogScope
        fun providesSaveCategoryUseCase(service: DbServiceImpl) = SaveCategoryUseCase(service)

        @Provides
        fun provideCardModule(
            app: Application,
            getAllCategoryUseCase: GetAllCategoryUseCase,
            saveCategoryUseCase: SaveCategoryUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(CategoryDialogViewModel::class.java) ->
                            CategoryDialogViewModel(
                                app, getAllCategoryUseCase, saveCategoryUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}