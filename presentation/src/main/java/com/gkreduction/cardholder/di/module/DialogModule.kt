package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.ui.dialog.remove.DialogRemoveViewModel
import com.gkreduction.cardholder.ui.dialog.preview.DialogPreviewViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class DialogModule {
    companion object {
        @Provides
        fun provideDialogModule(
            app: Application,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(DialogRemoveViewModel::class.java) ->
                            DialogRemoveViewModel(
                                app
                            ) as T

                        modelClass.isAssignableFrom(DialogPreviewViewModel::class.java) ->
                            DialogPreviewViewModel(
                                app
                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}