package com.rarnu.code

import com.rarnu.code.code.LatexRunner
import com.rarnu.code.code.extensions
import com.rarnu.code.code.runners
import com.rarnu.code.utils.*
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.content.*
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import java.io.File
import java.util.*

fun Routing.codeRouting() {


    get("/index") {
        val c = call.resolveFileBytes("index.html", "web")!!
        println("c => ${String(c)}")
        call.respond(call.resolveResource("index.html", "web") ?: "")
    }

    get("/hello") {
        val ses = session
        call.respondText { ses.uuid }
    }

    post("/code") {
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val code = p["code"] ?: ""
        val param = p["param"] ?: ""
        if (code == "" || language == "") {
            call.respondText { "{\"result\":1, \"message\":\"language or code is empty.\"}" }
        } else {
            val runner = call.runners[language.toLowerCase()]
            if (runner == null) {
                call.respondText { "{\"result\":1, \"message\":\"language not supported.\"}" }
            } else {
                val ext = call.extensions[language.toLowerCase()] ?: ""
                val uuid = session.uuid
                mkdir("$codePath/$uuid")
                val fname = "$uuid/${UUID.randomUUID()}$ext"
                val f = call.writeTempFile(fname, code)
                val ret = runner.run(f, param.split(" "))
                call.respondText { "{\"result\":0, \"output\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}" }
            }
        }
    }

    post("/codepack") {
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val fileCount = (p["filecount"] ?: "0").toInt()
        val param = p["param"] ?: ""
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
                val ret = runner.runPack(codemap, start, param.split(" "))
                val json = "{\"result\":0, \"output\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}"
                call.respondText { json }
            }
        }
    }

    // latex 转换
    post("/latex") {
        val file = call.receiveMultipart().readPart() as? PartData.FileItem
        if (file != null) {
            val uuid = session.uuid
            val ext = file.originalFileName?.substringAfterLast(".") ?: ""
            mkdir("$latexImagePath/$uuid")
            val fname = "$latexImagePath/$uuid/${UUID.randomUUID()}.$ext"
            val fdest = File(fname)
            file.save(fdest)
            file.dispose()
            val runner = call.runners["latex"]
            if (runner == null) {
                call.respondText { "{\"result\":0, \"latex\":\"\", \"error\":\"latex not supported.\"}" }
            } else {
                val ret = (runner as LatexRunner).imageToLatex(fdest, ext)
                val jsonstr =
                    "{\"result\":0, \"latex\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}"
                call.respondText { jsonstr }
            }
        } else {
            call.respondText { "{\"result\":1, \"latex\":\"\", \"error\":\"image not found.\"}" }
        }
    }

    // latex 转换（演示用）
    post("/latexsample") {
        mkdir("$latexImagePath/sample")
        val p = call.receiveParameters()
        val imgIndex = (p["imageIndex"] ?: "0").toInt()
        val fname = "images/$imgIndex.jpg"
        val fdest = File("$latexImagePath/sample/${UUID.randomUUID()}.jpg")
        val b = call.resolveFileSave(fdest, fname, "static")
        if (b) {
            val runner = call.runners["latex"]
            if (runner == null) {
                call.respondText { "{\"result\":0, \"latex\":\"\", \"error\":\"latex not supported.\"}" }
            } else {
                val ret = (runner as LatexRunner).imageToLatex(fdest, "jpg")
                val jsonstr =
                    "{\"result\":0, \"latex\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}"
                call.respondText { jsonstr }
            }
        } else {
            call.respondText { "{\"result\":1, \"latex\":\"\", \"error\":\"image not found.\"}" }
        }
    }
}
