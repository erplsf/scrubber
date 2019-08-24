package com.erplsf.scrubber

import org.jsoup.Jsoup

class ResponseParser {
    companion object {
        fun parse(response: String): WordDefinition {
            val doc =  Jsoup.parse(response)
            return if (doc.body().text() == "0") { // word doesn't exist in dictionary
                WordDefinition(false, "", HashMap())
            } else { // word exists, split and parse
                val word = doc.select(".hwd").first().text()
                val meanings = HashMap<String, String>()
                doc.select(".hom").forEach { element ->
                    val group = element.select(".pos").first().text()
                    val meaning = element.select(".def").first().text()
                    meanings[group] = meaning
                }
                WordDefinition(true, word, meanings)
            }
        }
    }
}