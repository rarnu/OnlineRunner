package com.rarnu.code.utils

import com.rarnu.code.CodeSession
import io.ktor.application.ApplicationCall
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import java.io.File
import java.util.*

inline val PipelineContext<*, ApplicationCall>.session: CodeSession
    get() = try {
        call.sessions.get() ?: CodeSession(UUID.randomUUID().toString())
    } catch (th: Throwable) {
        CodeSession(UUID.randomUUID().toString())
    }

fun PipelineContext<*, ApplicationCall>.mkdir(path: String) = makedir(path)

@UseExperimental(KtorExperimentalAPI::class)
fun PipelineContext<*, ApplicationCall>.config(key: String) = application.environment.config.property(key).getString()
