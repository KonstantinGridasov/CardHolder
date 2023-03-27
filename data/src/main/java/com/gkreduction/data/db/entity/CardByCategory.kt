package com.gkreduction.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CardByCategory(
    @Embedded
    var cat: CategoryDb,
    @Relation(
        entity = CardDb::class,
        parentColumn = "catId",
        entityColumn = "cardId",
    )
    var cards: List<CardDb>
)
