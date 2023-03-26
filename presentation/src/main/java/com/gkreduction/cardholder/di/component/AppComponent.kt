package com.gkreduction.cardholder.di.component

import android.app.Application
import com.gkreduction.cardholder.CardHolderApp
import com.gkreduction.cardholder.di.module.ActivityModule
import com.gkreduction.cardholder.di.module.AppModule
import com.gkreduction.cardholder.di.module.DbModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        DbModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
    ]
)
interface AppComponent : AndroidInjector<CardHolderApp> {
    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}