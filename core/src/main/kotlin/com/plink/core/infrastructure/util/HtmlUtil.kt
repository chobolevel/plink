package com.plink.core.infrastructure.util

import org.jsoup.Jsoup

object HtmlUtil {

    fun extractText(html: String): String {
        return Jsoup.parse(html)
            .text()
            .trim()
    }
}
