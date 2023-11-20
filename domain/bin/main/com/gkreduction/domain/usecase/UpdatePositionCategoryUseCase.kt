package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

class UpdatePositionCategoryUseCase(private val service: DbService) :
    UseCase<Boolean, List<Category>>() {
    override fun buildObservable(params: List<Category>?): Observable<Boolean> {
        return service.updatePositionCategory(params!!)
    }

}