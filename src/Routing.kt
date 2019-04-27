package com.rarnu.code

import com.rarnu.code.code.LatexRunner
import com.rarnu.code.code.extensions
import com.rarnu.code.code.runners
import com.rarnu.code.utils.*
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.content.resolveResource
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import java.io.File
import java.util.*

fun Routing.codeRouting() {

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
                val ret = runner.run(f, param)
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
                val ret = runner.runPack(codemap, start, param)
                call.respondText { "{\"result\":0, \"output\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}" }
            }
        }
    }

    post("/latex") {
        val p = call.receiveParameters()
        val imgFormat = p["format"] ?: "png"        // 图片格式，支持 png, jpg
        val source = p["source"] ?: "static"        // 判断是站内的还是用户上传的，static 表示站内，upload 表示上传
        var fdest: File? = null
        if (source == "static") {
            val imgIndex = (p["imageIndex"] ?: "0").toInt() // 站内图片的 index
            val fname = "images/$imgIndex.$imgFormat"
            fdest = call.resolveFile(fname, "static")
        } else {
            val uuid = session.uuid
            mkdir("$latexImagePath/$uuid")
            val fname = "$latexImagePath/$uuid/${UUID.randomUUID()}.png"
            val ftmp = File(fname)
            if (call.receiveMultipart().save("zip", ftmp)) {
                fdest = ftmp
            }
        }
        if (fdest != null) {
            val runner = call.runners["latex"]
            if (runner == null) {
                call.respondText { "{\"result\":0, \"latex\":\"\", \"error\":\"latex not supported.\"}" }
            } else {
                val ret = (runner as LatexRunner).imageToLatex(fdest, imgFormat)
                val jsonstr = "{\"result\":0, \"latex\":\"${ret.output.toJsonEncoded()}\", \"error\":\"${ret.error.toJsonEncoded()}\"}"
                call.respondText { jsonstr }
            }
        } else {
            call.respondText { "{\"result\":1, \"latex\":\"\", \"error\":\"image not found.\"}" }
        }

    }
}
