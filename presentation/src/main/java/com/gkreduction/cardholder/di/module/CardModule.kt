package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.CameraScope
import com.gkreduction.cardholder.di.scope.CardScope
import com.gkreduction.cardholder.ui.activity.card.CardViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.GetCardByIdUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class CardModule {
    companion object {
        @Provides
        @CardScope
        fun providesGetCardByIdUseCase(service: DbServiceImpl) = GetCardByIdUseCase(service)

        @Provides
        fun provideCardModule(
            app: Application,
            getCardByIdUseCase: GetCardByIdUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(CardViewModel::class.java) ->
                            CardViewModel(
                                app, getCardByIdUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}