package com.gkreduction.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CardWithCategory(
    @Embedded val cat: CategoryDb,
    @Relation(
        entity = CardDb::class,
        parentColumn = "catId",
        entityColumn = "categoryId",
    )
    var cards: List<CardDb>
)
