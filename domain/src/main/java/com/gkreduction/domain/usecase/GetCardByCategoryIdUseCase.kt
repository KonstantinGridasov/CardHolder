package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

class GetCardByCategoryIdUseCase(private val service: DbService) : UseCase<List<Card>, Long>() {
    override fun buildObservable(params: Long?):  Observable<List<Card>> {
        return service.getCardByCategoryId(params!!)
    }

}