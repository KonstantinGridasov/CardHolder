package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.AddScope
import com.gkreduction.cardholder.ui.activity.add.AddViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.GetCategoryByNameUseCase
import com.gkreduction.domain.usecase.SaveCardUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class AddModule {
    companion object {
        @Provides
        @AddScope
        fun providesSaveCardUseCase(service: DbServiceImpl) = SaveCardUseCase(service)

        @Provides
        @AddScope
        fun providesGetCategoryByNameUseCase(service: DbServiceImpl) =
            GetCategoryByNameUseCase(service)


        @Provides
        fun provideAddModule(
            app: Application,
            saveCardUseCase: SaveCardUseCase,
            getCategoryByNameUseCase: GetCategoryByNameUseCase,
            saveCategoryUseCase: SaveCategoryUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(AddViewModel::class.java) ->
                            AddViewModel(
                                app, saveCardUseCase,
                                getCategoryByNameUseCase,
                                saveCategoryUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}