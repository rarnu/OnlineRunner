package com.rarnu.code.utils

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import java.io.File


suspend fun MultiPartData.save(field: String, dest: File): Boolean {
    var ret = false
    this.forEachPart {
        if (it is PartData.FileItem) {
            if (it.name == field) {
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