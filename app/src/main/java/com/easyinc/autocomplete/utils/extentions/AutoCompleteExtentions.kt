package com.easyinc.autocomplete.utils.extentions

import java.util.*


fun String.convertToList(): List<String>{
        return this.trim()
            .splitToSequence(' ')
            .filter { it.isNotEmpty() && it.isNotBlank() }
            .toList()
            .map { list ->
                list.filter { char ->
                    char.isLetter()
                }
            }
    }

    fun List<String>.containsString(s: String): Boolean{
        return this.map {
            it.toLowerCase(Locale.ROOT)
        }.contains(
            s.toLowerCase(Locale.ROOT)
        )
    }

    fun List<String>.returnIfExcist(s: String): String?{
        return this.map {
            it.toLowerCase(Locale.ROOT)
        }.filter {
            it.startsWith(s.toLowerCase(Locale.ROOT))
        }.firstOrNull() {
            it.contains(s.toLowerCase(Locale.ROOT))
        }
    }

    fun String.formatStringSequence(): String{
        return this.trimEnd().dropLastWhile {
            it.isLetter()
        }
    }

