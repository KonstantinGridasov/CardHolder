package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.SplashScope
import com.gkreduction.cardholder.ui.activity.splash.SplashScreenViewModel
import com.gkreduction.data.shared.SharedServiceImpl
import com.gkreduction.domain.usecase.GetThemeUseCase
import dagger.Module
import dagger.Provides

@Module
abstract class SplashModule {

    companion object {

        @Provides
        @SplashScope
        fun providesGetThemeUseCase(service: SharedServiceImpl) = GetThemeUseCase(service)

        @Provides
        fun provideViewModelFactory(
            app: Application,
            getThemeUseCase: GetThemeUseCase,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(SplashScreenViewModel::class.java) ->
                            SplashScreenViewModel(
                                app,
                                getThemeUseCase,
                            ) as T


                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }
}