package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import com.rarnu.code.code.runners
import com.rarnu.common.asFileMkdirs
import com.rarnu.common.asFileWriteText
import com.rarnu.common.toJsonEncoded
import com.rarnu.ktor.config
import io.ktor.application.ApplicationCall
import io.ktor.request.receiveParameters
import java.io.File
import java.util.*

class CodeController(call: ApplicationCall) : ControllerIntf(call) {
    override suspend fun control(session: CodeSession): String {
        var ret = "{\"result\":1, \"message\":\"language or code is empty.\"}"
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val code = p["code"] ?: ""
        val param = p["param"] ?: ""
        val filename = p["filename"] ?: ""
        val uuid = session.uuid
        val codeid = UUID.randomUUID().toString()
        "${call.config("ktor.code.execPath")}/$uuid/$codeid".asFileMkdirs()
        if (code != "" && language != "") {
            val runner = call.runners[language.toLowerCase()]
            ret = if (runner == null) {
                "{\"result\":1, \"message\":\"language not supported.\"}"
            } else {
                val codemap = mutableMapOf<String, File>()
                val fpath = "$uuid/$codeid/$filename"
                val f = "${call.config("ktor.code.execPath")}/$fpath".asFileWriteText(code)
                codemap[filename] = f
                val execret = runner.runPack(codemap, filename, param.split(" "))
                "{\"result\":0, \"output\":\"${execret.output.toJsonEncoded()}\", \"error\":\"${execret.error.toJsonEncoded()}\"}"
            }
        }
        return ret
    }

}