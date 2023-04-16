package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

class GetCategoryByNameUseCase(private val service: DbService) : UseCase<Category?, String>() {
    override fun buildObservable(params: String?): Observable<Category?> {
        return service.getCategoryByName(params!!)
    }

}