package com.rarnu.code.utils

fun String.toJsonEncoded() = this.replace("\n", "\\n").replace("\"", "\\\"")