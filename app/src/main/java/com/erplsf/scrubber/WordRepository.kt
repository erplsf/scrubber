package com.erplsf.scrubber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@Singleton
class WordRepository {
    private val dictionaryAPI: DictionaryAPI = DictionaryAPI.create()

    fun getWordDefinition(word: String): LiveData<WordDefinition> {
        val data = MutableLiveData<WordDefinition>()

        dictionaryAPI.getWordDefinition(word).enqueue(object : Callback,
            retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                data.value = ResponseParser.parse(response.body()!!.string())
            }
        })

        return data
    }


}

// TODO: Retrofit -> Dagger 2