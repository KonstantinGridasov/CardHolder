package com.gkreduction.data.mapper

import android.util.Log
import com.gkreduction.data.db.entity.CardByCategory
import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CategoryDb
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode

class DbMapper {
    fun mapCard(list: List<CardByCategory>): List<Card> {
        val result = ArrayList<Card>()
        for (i in list) {
            for (k in i.cards) {
                val card = mapCard(k, i.cat)
                result.add(card)
            }
        }
        return result
    }

    fun mapCard(cardDb: CardByCategory): Card {
        return mapCard(cardDb.cards[0], cardDb.cat)
    }

    fun mapCard(cardDb: CardDb): Card {
        return mapCard(cardDb, CategoryDb())
    }


    fun mapCard(card: Card): CardByCategory {
        val list = ArrayList<CardDb>()
        list.add(getCardDb(card))
        return CardByCategory(getCategoryDb(card.category.catName), list)
    }


    fun mapCard(card: CardDb, category: CategoryDb): Card {
        return Card(
            color = card.color,
            cardId = card.cardId,
            category = mapCategory(category),
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            primary = getScanCode(true, card),
            secondary = getScanCode(false, card),
            existSecondary = card.existSecondary
        )


    }

    private fun getCategoryDb(catName: String) = CategoryDb(catName = catName)

    private fun getCardDb(card: Card): CardDb {
        return if (card.existSecondary) CardDb(
            cardId = card.cardId,
            categoryId = 0L,
            color = card.color,
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
            color = card.color,
            cardName = card.cardName,
            cardBaseInfo = card.cardBaseInfo,
            cardSecondInfo = card.cardSecondInfo,
            typeBase = card.primary.type,
            valueBase = card.primary.value,
            existSecondary = card.existSecondary
        )
    }


    private fun mapCategory(category: CategoryDb) =
        Category(catId = category.catId, catName = category.catName)


    private fun getScanCode(base: Boolean, cardDb: CardDb): ScanCode {
        return if (base) ScanCode(
            type = cardDb.typeBase, value = cardDb.valueBase
        )
        else ScanCode(
            type = cardDb.typeSecondary, value = cardDb.valueSecondary
        )
    }

}