package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class UpdateCategoryUseCase(private val service: DbService) : UseCase<Boolean, Category>() {
    override fun buildObservable(params: Category?): Observable<Boolean> {
        return service.updateCategory(params)
    }

}