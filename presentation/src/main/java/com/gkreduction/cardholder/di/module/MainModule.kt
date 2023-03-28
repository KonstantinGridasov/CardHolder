package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.MainScope
import com.gkreduction.cardholder.ui.activity.main.MainViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.GetAllCardsUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class MainModule {
    companion object {
        @Provides
        @MainScope
        fun providesGetUserChatsDbUseCase(
            service: DbServiceImpl,
        ) =
            GetAllCardsUseCase(service)


        @Provides
        fun provideMainModule(
            app: Application,
            getAllCardsUseCase: GetAllCardsUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(MainViewModel::class.java) ->
                            MainViewModel(
                                app,getAllCardsUseCase
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}