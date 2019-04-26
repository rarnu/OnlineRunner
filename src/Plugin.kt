package com.rarnu.code

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie

fun Application.installPlugin() {

    install(Sessions) {
        cookie<CodeSession>("CodeSession") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }

    install(PartialContent) {
        maxRangeCount = 10
    }
}