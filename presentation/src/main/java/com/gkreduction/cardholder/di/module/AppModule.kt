package com.gkreduction.cardholder.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Module
 class AppModule {

    @Provides
    @Singleton
    internal fun providesContext(application: DaggerApplication): Context =
        application.applicationContext

}
