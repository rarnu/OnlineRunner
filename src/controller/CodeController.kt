package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import com.rarnu.code.code.extensions
import com.rarnu.code.code.runners
import com.rarnu.kt.ktor.asFileMkdirs
import com.rarnu.kt.ktor.asFileWriteText
import com.rarnu.kt.ktor.config
import com.rarnu.kt.ktor.toJsonEncoded
import io.ktor.application.ApplicationCall
import io.ktor.request.receiveParameters
import java.util.*

class CodeController(call: ApplicationCall) : ControllerIntf(call) {
    override suspend fun control(session: CodeSession): String {
        var ret = "{\"result\":1, \"message\":\"language or code is empty.\"}"
        val p = call.receiveParameters()
        val language = p["language"] ?: ""
        val code = p["code"] ?: ""
        val param = p["param"] ?: ""
        if (code != "" && language != "") {
            val runner = call.runners[language.toLowerCase()]
            ret = if (runner == null) {
                "{\"result\":1, \"message\":\"language not supported.\"}"
            } else {
                val ext = call.extensions[language.toLowerCase()] ?: ""
                val uuid = session.uuid
                "${call.config("ktor.code.execPath")}/$uuid".asFileMkdirs()
                val fname = "$uuid/${UUID.randomUUID()}$ext"
                val f = "${call.config("ktor.code.execPath")}/$fname".asFileWriteText(code)!!
                val execret = runner.run(f, param.split(" "))
                "{\"result\":0, \"output\":\"${execret.output.toJsonEncoded()}\", \"error\":\"${execret.error.toJsonEncoded()}\"}"
            }
        }
        return ret
    }

}