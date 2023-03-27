package com.gkreduction.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gkreduction.data.db.dao.CardDao
import com.gkreduction.data.db.entity.CardDb
import com.gkreduction.data.db.entity.CategoryDb

@Database(
    entities = [CardDb::class, CategoryDb::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        private const val DB_NAME = "card-holder-db"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DB_NAME
            ).build()
        }
    }

}