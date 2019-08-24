package com.erplsf.scrubber

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordDefinition(
    val isValid: Boolean,
    @PrimaryKey val word: String,
    val meanings: HashMap<String, String>
)