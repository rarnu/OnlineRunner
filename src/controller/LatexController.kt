package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import com.rarnu.code.code.LatexRunner
import com.rarnu.code.code.runners
import com.rarnu.code.database.latex
import com.rarnu.common.asFileMkdirs
import com.rarnu.ktor.config
import com.rarnu.common.toJsonEncoded
import com.rarnu.ktor.save
import io.ktor.application.ApplicationCall
import io.ktor.http.content.PartData
import io.ktor.request.receiveMultipart
import java.io.File
import java.util.*

class LatexController(call: ApplicationCall) : ControllerIntf(call) {
    override suspend fun control(session: CodeSession): String {
        var ret = "{\"result\":1, \"latex\":\"\", \"error\":\"image not found.\"}"
        val file = call.receiveMultipart().readPart() as? PartData.FileItem
        if (file != null) {
            val uuid = session.uuid
            val ext = file.originalFileName?.substringAfterLast(".") ?: ""
            "${call.config("ktor.image.latexPath")}/$uuid".asFileMkdirs()
            val fdest = File("${call.config("ktor.image.latexPath")}/$uuid/${UUID.randomUUID()}.$ext")
            file.save(fdest)
            file.dispose()
            val runner = call.runners["latex"]
            ret = if (runner == null) {
                "{\"result\":0, \"latex\":\"\", \"error\":\"latex not supported.\"}"
            } else {
                val execret = (runner as LatexRunner).imageToLatex(call.application.latex, fdest, ext)
                "{\"result\":0, \"latex\":\"${execret.output.toJsonEncoded()}\", \"error\":\"${execret.error.toJsonEncoded()}\"}"
            }
        }
        return ret
    }

}