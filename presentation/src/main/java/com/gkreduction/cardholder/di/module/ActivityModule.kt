package com.gkreduction.cardholder.di.module

import com.gkreduction.cardholder.di.scope.CameraScope
import com.gkreduction.cardholder.di.scope.DialogScope
import com.gkreduction.cardholder.di.scope.MainScope
import com.gkreduction.cardholder.di.scope.SplashScope
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.activity.splash.SplashScreenActivity
import com.gkreduction.cardholder.ui.dialog.remove.DialogRemove
import com.gkreduction.cardholder.ui.dialog.preview.DialogPreview
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.GetAllCategoryUseCase
import com.gkreduction.domain.usecase.GetCardByIdUseCase
import com.gkreduction.domain.usecase.GetCategoryByNameUseCase
import com.gkreduction.domain.usecase.SaveCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributesMain(): MainActivity

    @SplashScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributesSplash(): SplashScreenActivity

    @CameraScope
    @ContributesAndroidInjector(modules = [CameraModule::class])
    abstract fun contributesCamera(): CameraActivity


    @DialogScope
    @ContributesAndroidInjector(modules = [DialogModule::class])
    abstract fun contributeDialogRemove(): DialogRemove


    @DialogScope
    @ContributesAndroidInjector(modules = [DialogModule::class])
    abstract fun contributeDialogPreview(): DialogPreview


    companion object {
        @Provides
        fun providesSaveCategoryUseCase(service: DbServiceImpl) = SaveCategoryUseCase(service)

        @Provides
        fun providesGetAllCategoryUseCase(service: DbServiceImpl) = GetAllCategoryUseCase(service)


        @Provides
        fun providesGetCategoryByNameUseCase(service: DbServiceImpl) =
            GetCategoryByNameUseCase(service)

        @Provides
        fun providesGetCardByIdUseCase(service: DbServiceImpl) = GetCardByIdUseCase(service)

    }
}
