package com.gkreduction.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "category_db", indices = [Index(value = ["catName"], unique = true)])
data class CategoryDb(
    @PrimaryKey(autoGenerate = true) var catId: Long = 0L,
    var serverCategoryId: Int = -1,
    var catName: String = "",

    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "modified_at") var modifiedAt: Long
) {
    override fun toString(): String {
        return catName + catId
    }
}