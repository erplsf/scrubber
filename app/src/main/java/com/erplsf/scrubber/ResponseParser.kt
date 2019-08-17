package com.erplsf.scrubber

import org.jsoup.Jsoup

class ResponseParser {
    companion object {
        fun parse(response: String): WordDefinition {
            val doc =  Jsoup.parse(response)
            return if (doc.body().text() == "0") { // word doesn't exist in dictionary
                WordDefinition(false, "", "", "")
            } else { // word exists, split and
                val word = doc.select(".hwd").first().text()
                val grammarGroup = doc.select(".pos").first().text()
                val meaning = doc.select(".def").first().text()
                WordDefinition(true, word, grammarGroup, meaning)
            }
        }
    }
}