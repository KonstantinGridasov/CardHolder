package com.gkreduction.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_db")
data class CardDb(
    @PrimaryKey(autoGenerate = true)
    var cardId: Long = 0L,

    @ColumnInfo
    var categoryId: Long = 0L,

    @ColumnInfo
    var colorStart: Int,
    @ColumnInfo
    var colorEnd: Int,

    @ColumnInfo
    var countOpen: Int = 0,

    @ColumnInfo
    var cardName: String = "",
    @ColumnInfo
    var cardBaseInfo: String = "",
    @ColumnInfo
    var cardSecondInfo: String = "",

    @ColumnInfo
    var typeBase: Int,
    @ColumnInfo
    var valueBase: String,

    @ColumnInfo
    var existSecondary: Boolean = false,
    @ColumnInfo
    var typeSecondary: Int = 0,
    @ColumnInfo
    var valueSecondary: String = ""


) {
    override fun toString(): String {
        return cardName + cardId + categoryId
    }
}