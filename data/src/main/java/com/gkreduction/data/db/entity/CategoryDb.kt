package com.gkreduction.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_db")
data class CategoryDb(
    @PrimaryKey(autoGenerate = true)
    var catId: Long = 0L,

    @ColumnInfo
    var catName: String = ""
) {
    override fun toString(): String {
        return catName + catId
    }
}