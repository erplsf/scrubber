package com.erplsf.scrubber

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val wordRepository: WordRepository = WordRepository(
        dictionaryAPI = DictionaryAPI.create(),
        wordDefinitionDao = WordDefinitionDatabase.getDatabase(application).wordDefinitionDao(),
        executor = Executors.newFixedThreadPool(3)
    )

    fun fetchWord(word: String): LiveData<WordDefinition> {
        return wordRepository.getWordDefinition(word)
    }
}