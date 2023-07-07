package com.gkreduction.data.db.dao

import androidx.room.*
import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CardWithCategory
import com.gkreduction.data.db.entity.CategoryDb

@Dao
interface CardDao {

    @Query("UPDATE card_db SET countOpen = countOpen + 1 WHERE card_db.cardId =:cardId ")
    fun updateCountCardByCardId(cardId: Long)

    @Transaction
    @Query("SELECT * FROM category_db JOIN card_db ON category_db.catId = card_db.categoryId WHERE card_db.cardId =:id LIMIT 1")
    fun getCardWithCategoryByCardId(id: Long): Map<CategoryDb, CardDb>

    @Transaction
    @Query("SELECT * FROM category_db")
    fun getALlCards(): List<CardWithCategory>

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catId >= 0")
    fun getALlCategory(): List<CategoryDb>

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catName LIKE :category")
    fun getAllCardByCategory(category: String): List<CardWithCategory>

    @Query("SELECT * FROM category_db WHERE category_db.catName LIKE :category LIMIT 1")
    fun findCategoryByName(category: String): CategoryDb

    @Transaction
    @Query("SELECT * FROM category_db WHERE category_db.catId =:categoryId")
    fun getCardsByCategoryId(categoryId: Long): List<CardWithCategory>

    @Query("SELECT COUNT() FROM category_db")
    fun countCategory(): Int

    @Transaction
    fun insert(cardByCategory: CardWithCategory) {
        val categoryId = if (isExistCategory(cardByCategory.cat.catName))
            findCategoryByName(cardByCategory.cat.catName).catId
        else
            insertCategory(cardByCategory.cat.catName)


        for (card in cardByCategory.cards) {
            card.categoryId = categoryId
            if (isExistCard(card.cardName))
                updateWithTimestamp(card)
            else
                insertWithTimestamp(card)
        }
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(card: CardDb)


    fun updateWithTimestamp(data: CardDb) {
        update(data.apply {
            modifiedAt = System.currentTimeMillis()
        })
    }


    @Query("SELECT EXISTS(SELECT * FROM card_db WHERE card_db.cardName LIKE :cardName)")
    fun isExistCard(cardName: String): Boolean

    fun insertWithTimestamp(data: CardDb) {
        insert(data.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: CardDb): Long

    @Query("SELECT EXISTS(SELECT * FROM category_db WHERE category_db.catName LIKE :category)")
    fun isExistCategory(category: String): Boolean


    fun insertCategory(name: String): Long {
        val position = countCategory()

        val category = CategoryDb(
            catId = 0L, catName = name,
            position = position,
            createdAt = System.currentTimeMillis(),
            modifiedAt = System.currentTimeMillis()
        )
        return insert(category)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: CategoryDb): Long

    @Query("UPDATE card_db SET categoryId = 1 WHERE categoryId LIKE :oldId ")
    fun updateCardToDefaultCategoryId(oldId: Long)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: CategoryDb)

    @Transaction
    fun updateCardDeleteCategory(categoryId: Long) {
        updateCardToDefaultCategoryId(categoryId)
        deleteCategoryById(categoryId)
    }

    @Query("DELETE FROM category_db WHERE catId = :id")
    fun deleteCategoryById(id: Long)

    @Delete
    fun delete(cardDb: CardDb)

    fun updatePositionCategory(mapCategory: List<CategoryDb>) {
        mapCategory.forEach { update(it) }
    }


}

