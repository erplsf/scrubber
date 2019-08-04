package com.erplsf.scrubber

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private lateinit var result: MutableLiveData<WordEntry>

    fun getResult(): MutableLiveData<WordEntry> {
        result.value = WordEntry("bae", "lover", true)

        return result
    }
}