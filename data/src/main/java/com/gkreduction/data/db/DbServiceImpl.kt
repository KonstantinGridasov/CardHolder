package com.gkreduction.data.db

import com.gkreduction.data.db.dao.CardDao
import com.gkreduction.data.db.entity.CategoryDb
import com.gkreduction.data.mapper.DbMapper
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.repository.DbService
import io.reactivex.Observable

class DbServiceImpl(
    private val cardDao: CardDao,
    private val dbMapper: DbMapper
) : DbService {
    override fun getAllCards(): Observable<List<Card>> {
        return Observable.just(cardDao.getALlCards())
            .map { dbMapper.mapCard(it) }
    }

    override fun getCardById(id: Long): Observable<Card?> {
        return Observable
            .just(cardDao.getCardByCategoryById(id))
            .map { dbMapper.mapCard(it) }
    }

    override fun saveCard(card: Card?): Observable<Boolean> {
        return if (card != null) {
            cardDao.insert(dbMapper.mapCard(card))
            Observable
                .just(true)
        } else
            Observable
                .just(false)
    }

    override fun saveCategory(card: Category): Observable<Boolean> {
        cardDao.insert(CategoryDb(catName = card.catName))
        return Observable
            .just(true)
    }
}
