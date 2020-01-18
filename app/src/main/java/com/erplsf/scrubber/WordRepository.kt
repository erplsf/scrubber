package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.WildcardType
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@Singleton
class WordRepository @Inject constructor(
    private val wordDefinitionDao: WordDefinitionDao,
    private val executor: Executor
) {
    private val dictionaryAPI: DictionaryAPI = DictionaryAPI.create()

    fun getWordDefinition(word: String) = liveData<WordDefinition> {

    }

    private suspend fun refreshWordDefinition(word: String) {
        withContext(Dispatchers.IO) {
            if (wordDefinitionDao.hasWordDefinition(word) == 0) {
                dictionaryAPI.getWordDefinition(word).enqueue(object : Callback,
                    retrofit2.Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        executor.execute {
                            val wordDefinition = ResponseParser.parse(response)
                            wordDefinitionDao.save(wordDefinition)
                        }
                    }
                })
            } else {
                wordDefinitionDao.load(word)
            }
        }
    }

//    companion object {
//        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//    }
}

// TODO: Retrofit -> Dagger 2