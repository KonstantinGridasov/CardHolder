package com.gkreduction.data.db

import com.gkreduction.data.db.dao.CardDao
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

    override fun getAllCategory(): Observable<List<Category>> {
        return Observable.just(true).flatMap {
            Observable.just(cardDao.getALlCategory())
                .map { dbMapper.mapCategory(it) }
        }

    }

    override fun saveCategory(catName: String?): Observable<Category> {
        return if (catName != null)
            Observable.just(true)
                .flatMap { Observable.just(cardDao.insertCategory(catName)) }
                .flatMap { Observable.just(cardDao.findCategoryByName(catName)) }
                .map { dbMapper.mapCategory(it) }
        else Observable.empty()
    }

    override fun getCategoryByName(categoryName: String): Observable<Category?> {
        return Observable.just(true)
            .flatMap { Observable.just(cardDao.isExistCategory(categoryName)) }
            .flatMap { exist ->
                if (exist)
                    return@flatMap Observable
                        .just(cardDao.findCategoryByName(categoryName))
                        .map { dbMapper.mapCategory(it) }
                else
                    return@flatMap Observable.empty()
            }

    }

    override fun getCardByCategoryId(params: Long): Observable<List<Card>> {
        return Observable.just(true)
            .flatMap {
                Observable
                    .just(cardDao.getCardsByCategoryId(params))
                    .map { dbMapper.mapCardWithCategoryToListCard(it) }
            }

    }

    override fun updateCategory(category: Category?): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                cardDao.update(dbMapper.mapCategory(category!!))
                Observable.just(true)
            }
    }

    override fun deleteCategoryById(categoryId: Long): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                cardDao.updateCardDeleteCategory(categoryId)
                Observable.just(true)
            }
    }

    override fun updateCard(card: Card): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                cardDao.updateWithTimestamp(dbMapper.getCardDb(card))
                Observable.just(true)
            }
    }

    override fun deleteCard(card: Card): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                cardDao.delete(dbMapper.getCardDb(card))
                Observable.just(true)
            }
    }

    override fun updatePositionCategory(params: List<Category>): Observable<Boolean> {
        return Observable.just(true)
            .flatMap {
                cardDao.updatePositionCategory(dbMapper.mapCategoryToDb(params))
                Observable.just(true)
            }
    }

}
