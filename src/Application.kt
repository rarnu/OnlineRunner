package com.rarnu.code

import com.rarnu.code.utils.config
import com.rarnu.code.utils.mkdir
import io.ktor.application.Application
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    codePath = config("ktor.code.execPath")
    mkdir(codePath)
    installPlugin()
    routing {
        resources("web")
        static("/static") { resources("static") }
        static { defaultResource("index.html", "web") }
        codeRouting()
    }
}