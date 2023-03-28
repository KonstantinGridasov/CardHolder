package com.gkreduction.domain.usecase

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

class GetCardById(private val service: DbService) : UseCase<Card?, Long>() {
    override fun buildObservable(params: Long?): Observable<Card?> {
        return service.getCardById(params!!)
    }

}