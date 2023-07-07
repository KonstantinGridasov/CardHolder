package com.gkreduction.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_db")
data class CardDb(
    @PrimaryKey(autoGenerate = true) var cardId: Long = 0L,

    var serverCardId: Int = -1,
    var categoryId: Long = 0L,
    var colorStart: Int,
    var countOpen: Int = 0,
    var cardName: String = "",
    var cardBaseInfo: String = "",
    var cardSecondInfo: String = "",
    var typeBase: Int,
    var valueBase: String,
    var existSecondary: Boolean = false,
    var typeSecondary: Int = 0,
    var valueSecondary: String = "",

    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "modified_at") var modifiedAt: Long


) {
    override fun toString(): String {
        return cardName + cardId + categoryId + createdAt + modifiedAt
    }
}