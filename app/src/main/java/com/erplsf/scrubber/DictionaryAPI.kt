package com.erplsf.scrubber

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Executors

interface DictionaryAPI {
    @GET("scrabble_api.php")
    fun getWordDefinition(@Query("word") word: String) : Call<ResponseBody>

    companion object Factory {
        fun create() : DictionaryAPI {
            val retrofit = Retrofit.Builder()
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://unikove.com/projects/scrabble_widget/")
                .build()

            return retrofit.create(DictionaryAPI::class.java)
        }
    }
}