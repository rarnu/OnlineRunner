package com.rarnu.code.utils

import java.io.File

fun makedir(path: String) {
    val f = File(path)
    if (!f.exists()) {
        f.mkdirs()
    }
}