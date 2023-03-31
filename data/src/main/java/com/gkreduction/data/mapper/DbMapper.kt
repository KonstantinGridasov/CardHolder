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
        return result
    }

    fun mapCardToCardWithCategory(card: Card): CardWithCategory {
        val list = ArrayList<CardDb>()
        list.add(getCardDb(card))
        return CardWithCategory(getCategoryDb(card.category.catName), list)
    }

    fun mapCategory(category: CategoryDb?): Category? {
        return if (category != null)
            Category(catId = category.catId, catName = category.catName)
        else
            null
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
            existSecondary = card.existSecondary,
            countOpen = card.countOpen
        )


    }

    private fun getCategoryDb(catName: String) = CategoryDb(catName = catName)

    private fun getCardDb(card: Card): CardDb {
        return if (card.existSecondary) CardDb(
            cardId = card.cardId,
            categoryId = 0L,
            colorStart = card.colorStart,
            colorEnd = card.colorEnd,
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            typeBase = card.primary.type,
            valueBase = card.primary.value,
            existSecondary = card.existSecondary,
            typeSecondary = card.secondary.type,
            valueSecondary = card.secondary.value,
        )
        else CardDb(
            cardId = card.cardId,
            categoryId = 0L,
            colorStart = card.colorStart,
            colorEnd = card.colorEnd,
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            typeBase = card.primary.type,
            valueBase = card.primary.value,
            existSecondary = card.existSecondary
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