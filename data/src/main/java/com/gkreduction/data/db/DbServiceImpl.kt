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
        return Observable.just(true)
            .flatMap {
                Observable.just(cardDao.getALlCards())
            }
            .map { dbMapper.mapCardWithCategoryToListCard(it) }

    }

    override fun getCardById(id: Long): Observable<Card?> {
        return Observable.just(true)
            .flatMap {
                Observable
                    .just(cardDao.updateCountCardByCardId(id))
                    .flatMap {
                        Observable.just(cardDao.getCardWithCategoryByCardId(id))
                    }
                    .map { dbMapper.mapDbToCard(it) }
            }
    }

    override fun saveCard(card: Card?): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                if (card != null) {
                    cardDao.insert(
                        dbMapper.mapCardToCardWithCategory(card)
                    )
                    Observable
                        .just(true)
                } else
                    Observable
                        .just(false)
            }

    }

    override fun saveCategory(card: Category): Observable<Boolean> {
        return Observable.just(true)
            .flatMap { Observable.just(cardDao.insert(CategoryDb(catName = card.catName))) }
            .flatMap { Observable.just(true) }

    }
}
