package com.erplsf.scrubber

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val wordRepository: WordRepository = WordRepository(
        wordDefinitionDao = WordDefinitionDatabase.getDatabase(application).wordDefinitionDao(),
        context = viewModelScope.coroutineContext + Dispatchers.IO
    )

    fun fetchWord(word: String): LiveData<WordDefinition> {
        return wordRepository.getWordDefinition(word)
    }

    fun fetchWordFromDb(word: String): LiveData<WordDefinition> {
        return wordRepository.getWordDefinitionFromDb(word)
    }
}