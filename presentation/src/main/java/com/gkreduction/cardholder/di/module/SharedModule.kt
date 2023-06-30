package com.gkreduction.cardholder.di.module

import android.app.Application
import com.gkreduction.data.shared.SharedServiceImpl
import com.gkreduction.data.shared.datasource.SharedDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedServiceImpl(
        source: SharedDataSource,
    ) = SharedServiceImpl(source)


    @Provides
    @Singleton
    fun provideSharedDataSource(app: Application) = SharedDataSource(app.applicationContext)


}