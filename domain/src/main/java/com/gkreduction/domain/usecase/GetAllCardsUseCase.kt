package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

open class GetAllCardsUseCase(private val service: DbService) : UseCase<List<Card>, Void>() {
    override fun buildObservable(params: Void?): Observable<List<Card>> {
        return service.getAllCards()
    }

}