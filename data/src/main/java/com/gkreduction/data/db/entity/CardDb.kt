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
    var cardName: String = "",
    @ColumnInfo
    var cardBaseInfo: String = "",
    @ColumnInfo
    var cardSecondInfo: String = "",

    @ColumnInfo
    var typeBase: Int,
    @ColumnInfo
    var widthBase: Int,
    @ColumnInfo
    var heightBase: Int,
    @ColumnInfo
    var valueBase: String,

    @ColumnInfo
    var existSecondary: Boolean = false,
    @ColumnInfo
    var typeSecondary: Int = 0,
    @ColumnInfo
    var widthSecondary: Int = 0,
    @ColumnInfo
    var heightSecondary: Int = 0,
    @ColumnInfo
    var valueSecondary: String = ""


)
