package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class SaveCategoryUseCase(private val service: DbService) : UseCase<Category, String>() {
    override fun buildObservable(params: String?): Observable<Category> {
        return service.saveCategory(params)
    }

}