package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class UpdateCardUseCase(private val service: DbService) : UseCase<Boolean, Card>() {
    override fun buildObservable(params: Card?): Observable<Boolean> {
        return service.updateCard(params!!)
    }

}