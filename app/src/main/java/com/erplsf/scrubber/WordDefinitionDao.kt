package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface WordDefinitionDao {
    @Insert(onConflict = REPLACE)
    fun save(wordDefinition: WordDefinition)

    @Query("SELECT * FROM wordDefinition WHERE word = :word")
    fun load(word: String): LiveData<WordDefinition>

    @Query("SELECT COUNT(*) FROM wordDefinition WHERE word == :word")
    fun hasWordDefinition(word: String): Int
}