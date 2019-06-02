package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import com.rarnu.code.code.LatexRunner
import com.rarnu.code.code.runners
import com.rarnu.code.database.latex
import com.rarnu.common.asFileMkdirs
import com.rarnu.ktor.config
import com.rarnu.ktor.resolveFileSave
import com.rarnu.common.toJsonEncoded
import io.ktor.application.ApplicationCall
import io.ktor.request.receiveParameters
import java.io.File
import java.util.*

class LatexSampleController(call: ApplicationCall) : ControllerIntf(call) {
    override suspend fun control(session: CodeSession): String {
        var ret = "{\"result\":1, \"latex\":\"\", \"error\":\"image not found.\"}"
        "${call.config("ktor.image.latexPath")}/sample".asFileMkdirs()
        val p = call.receiveParameters()
        val imgIndex = (p["imageIndex"] ?: "0").toInt()
        val fname = "images/$imgIndex.jpg"
        val fdest = File("${call.config("ktor.image.latexPath")}/sample/${UUID.randomUUID()}.jpg")
        val b = call.resolveFileSave(fdest, fname, "static")
        if (b) {
            val runner = call.runners["latex"]
            ret = if (runner == null) {
                "{\"result\":0, \"latex\":\"\", \"error\":\"latex not supported.\"}"
            } else {
                val execret = (runner as LatexRunner).imageToLatex(call.application.latex, fdest, "jpg")
                "{\"result\":0, \"latex\":\"${execret.output.toJsonEncoded()}\", \"error\":\"${execret.error.toJsonEncoded()}\"}"
            }
        }
        return ret
    }
}