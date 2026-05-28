package com.plink.core.common.util

import org.jsoup.Jsoup

object HtmlUtil {

    fun extractText(html: String): String {
        return Jsoup.parse(html)
            .text()
            .trim()
    }
}
