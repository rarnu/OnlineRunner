package com.rarnu.code.utils

import java.io.File

fun String.toJsonEncoded() = this.replace("\n", "\\n").replace("\"", "\\\"").replace("\\", "\\\\")
fun String.proj() = substring(0, 1).toUpperCase() + substring(1)

fun String.appendPathPart(part: String): String {
    val count =
        (if (isNotEmpty() && this[length - 1] == '/') 1 else 0) + (if (part.isNotEmpty() && part[0] == '/') 1 else 0)
    return when (count) {
        2 -> this + part.removePrefix("/")
        1 -> this + part
        else -> StringBuilder(length + part.length + 1).apply { append(this@appendPathPart); append('/'); append(part) }.toString()
    }
}

fun String.extension(): String {
    val indexOfName = lastIndexOf('/').takeIf { it != -1 } ?: lastIndexOf('\\').takeIf { it != -1 } ?: 0
    val indexOfDot = indexOf('.', indexOfName)
    return if (indexOfDot >= 0) substring(indexOfDot) else ""
}

fun String.save(dest: File) = dest.writeText(this)