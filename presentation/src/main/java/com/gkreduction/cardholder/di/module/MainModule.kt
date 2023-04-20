package com.gkreduction.cardholder.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gkreduction.cardholder.di.scope.MainScope
import com.gkreduction.cardholder.ui.activity.main.MainViewModel
import com.gkreduction.cardholder.ui.fragment.add.AddFragment
import com.gkreduction.cardholder.ui.fragment.add.AddViewModel
import com.gkreduction.cardholder.ui.fragment.card.CardFragment
import com.gkreduction.cardholder.ui.fragment.card.CardViewModel
import com.gkreduction.cardholder.ui.fragment.category.CategoryFragment
import com.gkreduction.cardholder.ui.fragment.category.CategoryViewModel
import com.gkreduction.cardholder.ui.fragment.home.HomeFragment
import com.gkreduction.cardholder.ui.fragment.home.HomeViewModel
import com.gkreduction.data.db.DbServiceImpl
import com.gkreduction.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeAddFragment(): AddFragment

    @ContributesAndroidInjector
    abstract fun contributeCardFragment(): CardFragment

    companion object {
        @Provides
        @MainScope
        fun providesGetUserChatsDbUseCase(service: DbServiceImpl) = GetAllCardsUseCase(service)

        @Provides
        @MainScope
        fun providesGetCardByCategoryIdUseCase(service: DbServiceImpl) =
            GetCardByCategoryIdUseCase(service)


        @Provides
        @MainScope
        fun providesUpdateCategoryUseCase(service: DbServiceImpl) = UpdateCategoryUseCase(service)

        @Provides
        @MainScope
        fun providesDeleteCategoryUseCase(service: DbServiceImpl) = DeleteCategoryUseCase(service)

        @Provides
        @MainScope
        fun providesSaveCardUseCase(service: DbServiceImpl) = SaveCardUseCase(service)


        @Provides
        @MainScope
        fun providesUpdateCardUseCase(service: DbServiceImpl) = UpdateCardUseCase(service)

        @Provides
        @MainScope
        fun providesDeleteCardUseCase(service: DbServiceImpl) = DeleteCardUseCase(service)


        @Provides
        fun provideMainModule(
            app: Application,
            getAllCardsUseCase: GetAllCardsUseCase,
            getAllCategoryUseCase: GetAllCategoryUseCase,
            saveCategoryUseCase: SaveCategoryUseCase,
            getCardByCategoryIdUseCase: GetCardByCategoryIdUseCase,
            updateCategoryUseCase: UpdateCategoryUseCase,
            deleteCategoryUseCase: DeleteCategoryUseCase,
            saveCardUseCase: SaveCardUseCase,
            updateCardUseCase: UpdateCardUseCase,
            getCategoryByNameUseCase: GetCategoryByNameUseCase,
            getCardByIdUseCase: GetCardByIdUseCase,
            deleteCardUseCase: DeleteCardUseCase

        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return when {
                        modelClass.isAssignableFrom(MainViewModel::class.java) ->
                            MainViewModel(
                                app, getAllCardsUseCase, getAllCategoryUseCase,
                                saveCategoryUseCase, getCardByCategoryIdUseCase
                            ) as T

                        modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                            HomeViewModel(
                                app, getAllCardsUseCase, getAllCategoryUseCase,
                                saveCategoryUseCase, getCardByCategoryIdUseCase
                            ) as T

                        modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                            CategoryViewModel(
                                app,
                                getAllCategoryUseCase,
                                saveCategoryUseCase,
                                updateCategoryUseCase,
                                deleteCategoryUseCase
                            ) as T
                        modelClass.isAssignableFrom(AddViewModel::class.java) ->
                            AddViewModel(
                                app, saveCardUseCase,
                                getCategoryByNameUseCase,
                                saveCategoryUseCase,
                                getCardByIdUseCase, updateCardUseCase
                            ) as T

                        modelClass.isAssignableFrom(CardViewModel::class.java) ->
                            CardViewModel(
                                app, getCardByIdUseCase,
                                deleteCardUseCase

                            ) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }


}