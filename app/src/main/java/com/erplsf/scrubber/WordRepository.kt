package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@Singleton
class WordRepository @Inject constructor(
    private val dictionaryAPI: DictionaryAPI,
    private val wordDefinitionDao: WordDefinitionDao,
    private val executor: Executor
) {
    fun getWordDefinition(word: String): LiveData<WordDefinition> {
        refreshWordDefinition(word)

        return wordDefinitionDao.load(word)
    }

    private fun refreshWordDefinition(word: String) {
        executor.execute {
            if (wordDefinitionDao.hasWordDefinition(word) == 0) {
                dictionaryAPI.getWordDefinition(word).enqueue(object : Callback,
                    retrofit2.Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        executor.execute {
                            wordDefinitionDao.save(ResponseParser.parse(response.body()!!.string()))
                        }
                    }
                })
            }
        }
    }

//    companion object {
//        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//    }
}

// TODO: Retrofit -> Dagger 2