package com.rarnu.code

import com.rarnu.common.asFileMkdirs
import com.rarnu.ktor.config
import com.rarnu.ktor.installPlugin
import io.ktor.application.Application
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    config("ktor.code.execPath").asFileMkdirs()
    config("ktor.image.latexPath").asFileMkdirs()
    installPlugin<CodeSession>(sessionIdentifier = "CodeSession", headers = mapOf("X-Engine" to "Ktor")) {  }
    routing {
        resources("web")
        resources("static")
        static("/static") { resources("static") }
        static { defaultResource("index.html", "web") }
        codeRouting()
    }
}
