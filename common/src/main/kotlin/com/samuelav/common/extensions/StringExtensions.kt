package com.samuelav.common.extensions

import java.util.Locale.ROOT

fun String.titleCase() = replaceFirstChar { it.titlecase(ROOT) }