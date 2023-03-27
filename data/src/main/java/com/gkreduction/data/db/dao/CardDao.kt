package com.gkreduction.data.db.dao

import androidx.room.*
import com.gkreduction.data.db.entity.CardByCategory
import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CategoryDb

@Dao
interface CardDao {
    @Transaction
    @Query("SELECT * FROM category_db,card_db WHERE card_db.cardId = :id ")
    fun getCardByCategoryById(id: Long): CardByCategory

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catId >= 0")
    fun getALlCards(): List<CardByCategory>

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catId >= 0")
    fun getALlCategory(): List<CategoryDb>

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catName LIKE :category")
    fun getAllCardByCategory(category: String): List<CardByCategory>

    @Query("SELECT * FROM category_db WHERE category_db.catName LIKE :category LIMIT 1")
    fun findCategoryByName(category: String): CategoryDb

    @Transaction
    fun insert(cardByCategory: CardByCategory) {
        val categoryId = if (isExistCategory(cardByCategory.cat.catName))
            findCategoryByName(cardByCategory.cat.catName).catId else 0L
        for (card in cardByCategory.cards) {
            card.categoryId = categoryId
            if (isExistCard(card.cardName))
                update(card)
            else
                insert(card)
        }
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(card: CardDb)

    @Query("SELECT EXISTS(SELECT * FROM card_db WHERE card_db.cardName LIKE :cardName)")
    fun isExistCard(cardName: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: CardDb): Long

    @Query("SELECT EXISTS(SELECT * FROM category_db WHERE category_db.catName LIKE :category)")
    fun isExistCategory(category: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: CategoryDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: CategoryDb)

}