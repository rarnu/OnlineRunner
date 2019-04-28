package com.rarnu.code.utils

import com.sun.org.apache.xpath.internal.operations.Bool
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import java.io.File

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

suspend fun PartData.FileItem.save(dest: File): Boolean {
    var ret = false
    this.streamProvider().use { input ->
        dest.outputStream().buffered().use { output ->
            ret = input.copyToSuspend(output) > 0
        }
    }
    return ret
}