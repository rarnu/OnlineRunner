package com.rarnu.code

import com.rarnu.kt.ktor.asFileMkdirs
import com.rarnu.kt.ktor.config
import com.rarnu.kt.ktor.installPlugin
import io.ktor.application.Application
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing
import java.io.File

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
