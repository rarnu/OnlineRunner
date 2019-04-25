package com.rarnu.code

import com.rarnu.code.code.compilers
import com.rarnu.code.code.extensions
import com.rarnu.code.code.runners
import com.rarnu.code.utils.*
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post
import java.io.File
import java.util.*

fun Routing.codeRouting() {

    post("/code") {
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val code = p["code"] ?: ""
        if (code == "" || language == "") {
            call.respondText { "{\"result\":1, \"message\":\"language or code is empty.\"}" }
        } else {
            val runner = call.runners[language.toLowerCase()]
            if (runner == null) {
                call.respondText { "{\"result\":1, \"message\":\"language not supported.\"}" }
            } else {
                val ext = call.extensions[language.toLowerCase()] ?: ""
                val cmd = call.compilers[language.toLowerCase()] ?: ""
                val uuid = session.uuid
                mkdir("$codePath/$uuid")
                val fname = "$uuid/${UUID.randomUUID()}$ext"
                val f = call.writeTempFile(fname, code)
                val ret = runner.run(cmd, f)
                call.respondText { "{\"result\":0, \"output\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}" }
            }
        }
    }

    post("/codepack") {
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val fileCount = (p["filecount"] ?: "0").toInt()
        val uuid = session.uuid
        val codeid = UUID.randomUUID().toString()
        val dirPath = "$codePath/$uuid/$codeid"
        mkdir(dirPath)
        if (language == "" || fileCount == 0) {
            call.respondText { "{\"result\":1, \"message\":\"language or fileCount is empty.\"}" }
        } else {
            val runner = call.runners[language.toLowerCase()]
            if (runner == null) {
                call.respondText { "{\"result\":1, \"message\":\"language not supported.\"}" }
            } else {
                val codemap = mutableMapOf<String, File>()
                (0 until fileCount).forEach { i ->
                    val fname = p["filename$i"] ?: ""
                    val fpath = "$uuid/$codeid/$fname"
                    val code = p["code$i"] ?: ""
                    val f = call.writeTempFile(fpath, code)
                    codemap[fname] = f
                }
                val start = p["start"] ?: ""
                val cmd = call.compilers[language.toLowerCase()] ?: ""
                val ret = runner.runPack(cmd, codemap, start)
                call.respondText { "{\"result\":0, \"output\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}" }
            }
        }
    }
}