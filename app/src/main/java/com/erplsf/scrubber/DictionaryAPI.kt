package com.erplsf.scrubber

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryAPI {
    @GET("scrabble_api.php")
    fun getWordDefinition(@Query("word") word: String) : Call<ResponseBody>

    companion object Factory {
        fun create() : DictionaryAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://unikove.com/projects/scrabble_widget/")
                .build()

            return retrofit.create(DictionaryAPI::class.java)
        }
    }
}