package com.gkreduction.data.mapper

import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CardWithCategory
import com.gkreduction.data.db.entity.CategoryDb
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode

class DbMapper {
    fun mapCardWithCategoryToListCard(list: List<CardWithCategory>): List<Card> {
        val result = ArrayList<Card>()
        for (i in list) {
            for (k in i.cards) {
                val card = mapCard(k, i.cat)
                result.add(card)
            }
        }
        result.sortWith(compareBy { -it.countOpen })
        return result
    }


    fun mapDbToCard(map: Map<CategoryDb, CardDb>): Card? {
        var category: CategoryDb? = null
        var card: CardDb? = null
        for (k in map.keys) {
            category = k
            card = map[k]
            break
        }
        return if (card != null && category != null)
            mapCard(card, category)
        else
            null
    }

    fun mapCategory(list: List<CategoryDb>): List<Category> {
        val result = ArrayList<Category>()
        for (i in list)
            result.add(mapCategory(i)!!)

        result.sortWith(compareBy { it.position })
        return result
    }

    fun mapCardToCardWithCategory(card: Card): CardWithCategory {
        val list = ArrayList<CardDb>()
        list.add(getCardDb(card))
        return CardWithCategory(mapCategory(card.category), list)
    }

    fun mapCategory(category: CategoryDb?): Category? {
        return if (category != null)
            Category(
                catId = category.catId,
                catName = category.catName,
                position = category.catId,
                createdAt = category.createdAt,
                modifiedAt = category.modifiedAt
            )
        else
            null
    }

    fun mapCategory(category: Category): CategoryDb {
        return CategoryDb(
            catId = category.catId, catName = category.catName,
            createdAt = category.createdAt,
            modifiedAt = category.modifiedAt
        )

    }

    fun getCardDb(card: Card): CardDb {
        return CardDb(
            cardId = card.cardId,
            categoryId = card.category.catId,
            colorStart = card.colorStart,
            colorEnd = card.colorEnd,
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            typeBase = card.primary?.type ?: 0,
            valueBase = card.primary?.value ?: "",
            existSecondary = card.secondary == null,
            typeSecondary = card.secondary?.type ?: 0,
            valueSecondary = card.secondary?.value ?: "",
            createdAt = card.createdAt,
            modifiedAt = card.modifiedAt
        )

    }


    private fun mapCard(card: CardDb, category: CategoryDb): Card {
        return Card(
            colorStart = card.colorStart,
            colorEnd = card.colorEnd,
            cardId = card.cardId,
            category = mapCategory(category)!!,
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            primary = getScanCode(true, card),
            secondary = getScanCode(false, card),
            existSecondary = card.valueSecondary.isNotEmpty(),
            countOpen = card.countOpen,
            createdAt = card.createdAt,
            modifiedAt = card.modifiedAt
        )


    }


    private fun getScanCode(base: Boolean, cardDb: CardDb): ScanCode {
        return if (base) ScanCode(
            type = cardDb.typeBase, value = cardDb.valueBase
        )
        else ScanCode(
            type = cardDb.typeSecondary, value = cardDb.valueSecondary
        )
    }


}