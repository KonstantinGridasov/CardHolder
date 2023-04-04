package com.gkreduction.domain.usecase

import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class DeleteCategoryUseCase(private val service: DbService) : UseCase<Boolean, Long>() {
    override fun buildObservable(params: Long?): Observable<Boolean> {
        return service.deleteCategoryById(params!!)
    }

}