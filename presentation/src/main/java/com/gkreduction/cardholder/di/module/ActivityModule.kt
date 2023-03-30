package com.gkreduction.cardholder.di.module

import com.gkreduction.cardholder.di.scope.*
import com.gkreduction.cardholder.ui.activity.card.CardActivity
import com.gkreduction.cardholder.ui.activity.add.AddActivity
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.dialog.CategoryDialog
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

    @DialogScope
    @ContributesAndroidInjector(modules = [DialogModule::class])
    abstract fun contributeCategoryDialog(): CategoryDialog

}
