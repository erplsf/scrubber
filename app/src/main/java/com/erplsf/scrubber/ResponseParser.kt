package com.erplsf.scrubber

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.lang.Exception

class EmptyResponseBodyException(message: String) : Exception(message)

class ResponseParser {
    companion object {
        fun parse(response: Response<ResponseBody>): Result<WordDefinition> {
            if (response.body() == null) {
                return Result.failure(throw EmptyResponseBodyException)
            }

            val body = response.body()!!.string()
            val doc = Jsoup.parse(body)

            return if (doc.body().text() == "0") { // word doesn't exist in dictionary
                Result.success(WordDefinition(isValid = false))
            } else { // word exists, split and parse
                val word = doc.select(".hwd").first().text()
                val meanings = HashMap<String, String>()
                doc.select(".hom").forEach { element ->
                    val group = element.select(".pos").first().text()
                    val meaning = element.select(".def").first().text()
                    meanings[group] = meaning
                }
                Result.success(WordDefinition(isValid = true, word = word, meanings = meanings))
            }
        }
    }
}