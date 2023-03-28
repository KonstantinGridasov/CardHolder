package com.gkreduction.data.mapper

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
            val card = mapCard(i)
            if (card != null)
                result.add(card)
        }
        return result
    }

    fun mapCard(card: Card): CardByCategory {
        val list = ArrayList<CardDb>()
        list.add(getCardDb(card))
        return CardByCategory(getCategoryDb(card.category.catName), list)
    }

    fun mapCard(card: CardByCategory): Card? {
        return if (card.cards.isNotEmpty())
            Card(
                color = card.cards[0].color,
                cardId = card.cards[0].cardId,
                category = mapCategory(card.cat),
                cardName = card.cards[0].cardName,
                cardBaseInfo = card.cards[0].cardBaseInfo,
                cardSecondInfo = card.cards[0].cardSecondInfo,
                primary = getScanCode(true, card.cards[0]),
                secondary = getScanCode(false, card.cards[0]),
                existSecondary = card.cards[0].existSecondary
            )
        else
            return null


    }

    private fun getCategoryDb(catName: String) = CategoryDb(catName = catName)

    private fun getCardDb(card: Card): CardDb {
        return if (card.existSecondary)
            CardDb(

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
        return if (base)
            ScanCode(
                type = cardDb.typeBase,
                value = cardDb.valueBase
            )
        else
            ScanCode(
                type = cardDb.typeSecondary,
                value = cardDb.valueSecondary
            )
    }

}