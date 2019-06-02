package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import com.rarnu.code.code.runners
import com.rarnu.common.asFileMkdirs
import com.rarnu.common.asFileWriteText
import com.rarnu.ktor.config
import com.rarnu.common.toJsonEncoded
import io.ktor.application.ApplicationCall
import io.ktor.request.receiveParameters
import java.io.File
import java.util.*

class CodePackController(call: ApplicationCall) : ControllerIntf(call) {
    override suspend fun control(session: CodeSession): String {
        var ret = "{\"result\":1, \"message\":\"language or fileCount is empty.\"}"
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val fileCount = (p["filecount"] ?: "0").toInt()
        val param = p["param"] ?: ""
        val uuid = session.uuid
        val codeid = UUID.randomUUID().toString()
        "${call.config("ktor.code.execPath")}/$uuid/$codeid".asFileMkdirs()
        if (language != "" && fileCount != 0) {
            val runner = call.runners[language.toLowerCase()]
            ret = if (runner == null) {
                "{\"result\":1, \"message\":\"language not supported.\"}"
            } else {
                val codemap = mutableMapOf<String, File>()
                (0 until fileCount).forEach { i ->
                    val fname = p["filename$i"] ?: ""
                    val fpath = "$uuid/$codeid/$fname"
                    val code = p["code$i"] ?: ""
                    val f = "${call.config("ktor.code.execPath")}/$fpath".asFileWriteText(code)!!
                    codemap[fname] = f
                }
                val start = p["start"] ?: ""
                val execret = runner.runPack(codemap, start, param.split(" "))
                "{\"result\":0, \"output\":\"${execret.output.toJsonEncoded()}\", \"error\":\"${execret.error.toJsonEncoded()}\"}"
            }
        }
        return ret
    }

}