package com.erplsf.scrubber

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_main_activity.*
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {
    private val wordRepository: WordRepository = WordRepository()

    fun fetchWord(word: String): LiveData<WordDefinition> {
        return wordRepository.getWordDefinition(word)
    }
}