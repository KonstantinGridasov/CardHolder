package com.gkreduction.domain.repository

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import io.reactivex.Observable

interface DbService {
    fun getAllCards(): Observable<List<Card>>
    fun getCardById(id: Long): Observable<Card?>
    fun saveCard(card: Card?): Observable<Boolean>
    fun saveCategory(card: Category): Observable<Boolean>

}