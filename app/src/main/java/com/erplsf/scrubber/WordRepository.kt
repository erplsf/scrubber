package com.erplsf.scrubber

import androidx.lifecycle.liveData
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class WordRepository @Inject constructor(
    private val wordDefinitionDao: WordDefinitionDao,
    private val context: CoroutineContext
) {
    private val dictionaryAPI: DictionaryAPI = DictionaryAPI.create()

    fun getWordDefinition(word: String) = liveData(context) {
        val disposable = emitSource(
            wordDefinitionDao.load(word)
        )

        if (!wordDefinitionDao.hasWordDefinition(word)) {
            disposable.dispose()
            val response = dictionaryAPI.getWordDefinition(word).execute()
            val wordDefinition = ResponseParser.parse(response)

            wordDefinitionDao.save(wordDefinition)

            emitSource(
                wordDefinitionDao.load(word)
            )
        }
    }

//    companion object {
//        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//    }
}

// TODO: Retrofit -> Dagger 2