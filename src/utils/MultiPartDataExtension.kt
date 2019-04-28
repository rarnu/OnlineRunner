package com.rarnu.code.utils

import io.ktor.http.content.*
import java.io.File
import javax.servlet.http.Part


suspend fun MultiPartData.save(field: String, dest: File): Boolean {
    var ret = false
    this.forEachPart {
        println("part = $it")
        if (it is PartData.FileItem) {
            println("part.name = ${it.name}")
            if (it.name == field) {
                println("save part")
                it.streamProvider().use { input ->
                    dest.outputStream().buffered().use { output ->
                        ret = input.copyToSuspend(output) > 0
                    }
                }
            }
            it.dispose()
        }
    }
    return ret
}