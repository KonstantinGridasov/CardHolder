package com.gkreduction.cardholder.di.module

import com.gkreduction.cardholder.di.scope.CameraScope
import com.gkreduction.cardholder.di.scope.MainScope
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributesMain(): MainActivity

    @CameraScope
    @ContributesAndroidInjector(modules = [CameraModule::class])
    abstract fun contributesCamera(): CameraActivity
}