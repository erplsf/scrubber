package com.erplsf.scrubber

class WordEntry(private val word : String, private val meaning: String, private val valid : Boolean) {
    fun isValidWord() : Boolean {
        return valid
    }

    fun meaning(): String = meaning
}