package com.erplsf.scrubber

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WordDefinition::class], version = 1)
@TypeConverters(HashMapConverter::class)
abstract class WordDefinitionDatabase : RoomDatabase() {
    abstract fun wordDefinitionDao() : WordDefinitionDao

    companion object {
        @Volatile
        private var INSTANCE: WordDefinitionDatabase? = null

        fun getDatabase(context: Context): WordDefinitionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDefinitionDatabase::class.java,
                    "scrubber_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}