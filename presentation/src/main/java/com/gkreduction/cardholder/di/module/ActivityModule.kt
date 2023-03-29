package com.gkreduction.cardholder.di.module

import com.gkreduction.cardholder.di.scope.AddScope
import com.gkreduction.cardholder.di.scope.CameraScope
import com.gkreduction.cardholder.di.scope.CardScope
import com.gkreduction.cardholder.di.scope.MainScope
import com.gkreduction.cardholder.ui.activity.card.CardActivity
import com.gkreduction.cardholder.ui.activity.add.AddActivity
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

    @AddScope
    @ContributesAndroidInjector(modules = [AddModule::class])
    abstract fun contributesAdd(): AddActivity

    @CardScope
    @ContributesAndroidInjector(modules = [CardModule::class])
    abstract fun contributesCard(): CardActivity
}