package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDefinitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(wordDefinition: WordDefinition)

    @Query("SELECT * FROM wordDefinition WHERE word = :word LIMIT 1")
    fun load(word: String): LiveData<WordDefinition>

    @Query("SELECT COUNT(*) FROM wordDefinition WHERE word = :word")
    suspend fun hasWordDefinition(word: String): Boolean
}