package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        emitSource(
            wordDefinitionDao.load(word)
        )

        if (!wordDefinitionDao.hasWordDefinition(word)) {
            dictionaryAPI.getWordDefinition(word).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val wordDefinition = ResponseParser.parse(response)
                    if (wordDefinition.isValid) {
                        wordDefinitionDao.save(wordDefinition)
                    } else {
                        wordDefinitionDao.save(WordDefinition(word = word, isValid = false))
                    }
                }
            })
        }
    }

    fun getWordDefinitionFromDb(word: String): LiveData<WordDefinition> {
        return wordDefinitionDao.load(word)
    }


//    companion object {
//        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//    }
}

// TODO: Retrofit -> Dagger 2