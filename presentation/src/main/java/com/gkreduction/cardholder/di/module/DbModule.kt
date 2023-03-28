package com.gkreduction.cardholder.di.module

import android.app.Application
import com.gkreduction.data.db.AppDatabase
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.data.db.dao.CardDao
import com.gkreduction.data.mapper.DbMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDbServiceImpl(
        cardDao: CardDao,
        dbMapper: DbMapper
    ) = DbServiceImpl(cardDao, dbMapper)

    @Provides
    @Singleton
    fun provideDbMapper() = DbMapper()

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) = AppDatabase.getInstance(app.applicationContext)


    @Provides
    @Singleton
    fun provideCardDao(appDatabase: AppDatabase): CardDao = appDatabase.cardDao()
}