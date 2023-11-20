package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class GetAllCategoryUseCase(private val service: DbService) : UseCase<List<Category>, Void>() {
    override fun buildObservable(params: Void?): Observable<List<Category>> {
        return service.getAllCategory()
    }

}