package com.samuelav.common.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale.ROOT

fun LocalDateTime.format(pattern: String, capitalize: Boolean = false): String =
    format(DateTimeFormatter.ofPattern(pattern)).replaceFirstChar {
        if (capitalize) it.titlecase(ROOT) else it.toString()
    }