package com.gkreduction.data.mapper

import com.gkreduction.data.db.entity.CardByCategory
import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CategoryDb
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode

class DbMapper {
    fun mapCard(card: CardByCategory): Card? {
        return if (card.cards.isNotEmpty())
            Card(
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

    fun mapCategory(category: CategoryDb) =
        Category(catId = category.catId, catName = category.catName)


    private fun getScanCode(base: Boolean, cardDb: CardDb): ScanCode {
        return if (base)
            ScanCode(
                type = cardDb.typeBase,
                width = cardDb.widthBase,
                height = cardDb.heightBase,
                value = cardDb.valueBase
            )
        else
            ScanCode(
                type = cardDb.typeSecondary,
                width = cardDb.widthSecondary,
                height = cardDb.heightSecondary,
                value = cardDb.valueSecondary
            )
    }

}