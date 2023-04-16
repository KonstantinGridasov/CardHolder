package com.gkreduction.domain.repository

import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import io.reactivex.Observable

interface DbService {
    fun getAllCards(): Observable<List<Card>>
    fun getCardById(id: Long): Observable<Card?>
    fun saveCard(card: Card?): Observable<Boolean>

    fun getAllCategory(): Observable<List<Category>>
    fun saveCategory(catName: String?): Observable<Category>
    fun getCategoryByName(categoryName: String): Observable<Category?>
    fun getCardByCategoryId(params: Long): Observable<List<Card>>

    fun updateCategory(category: Category?): Observable<Boolean>
    fun deleteCategoryById(categoryId: Long): Observable<Boolean>
    fun updateCard(card: Card): Observable<Boolean>
    fun deleteCard(card: Card): Observable<Boolean>

}