package com.rarnu.code.utils

import com.rarnu.code.codePath
import io.ktor.application.ApplicationCall
import io.ktor.util.KtorExperimentalAPI
import java.io.File

@UseExperimental(KtorExperimentalAPI::class)
fun ApplicationCall.writeTempFile(fname: String, text: String): File {
    val ftmp = File(File(codePath), fname)
    ftmp.writeText(text)
    return ftmp
}