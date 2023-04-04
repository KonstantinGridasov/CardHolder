package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.EditScope
import com.gkreduction.cardholder.ui.activity.edit_card.EditCardViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
abstract class EditModule {
    companion object {
        @Provides
        @EditScope
        fun providesSaveCardUseCase(service: DbServiceImpl) = SaveCardUseCase(service)


        @Provides
        @EditScope
        fun providesUpdateCardUseCase(service: DbServiceImpl) = UpdateCardUseCase(service)

        @Provides
        fun provideAddModule(
            app: Application,
            saveCardUseCase: SaveCardUseCase,
            getCategoryByNameUseCase: GetCategoryByNameUseCase,
            saveCategoryUseCase: SaveCategoryUseCase,
            getCardByIdUseCase: GetCardByIdUseCase,
            updateCardUseCase: UpdateCardUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(EditCardViewModel::class.java) ->
                            EditCardViewModel(
                                app, saveCardUseCase,
                                getCategoryByNameUseCase,
                                saveCategoryUseCase,
                                getCardByIdUseCase,updateCardUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}