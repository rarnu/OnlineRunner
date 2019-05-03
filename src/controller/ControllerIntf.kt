package com.rarnu.code.controller

import com.rarnu.code.CodeSession
import io.ktor.application.ApplicationCall

abstract class ControllerIntf(val call: ApplicationCall) {
    abstract suspend fun control(session: CodeSession): String
}