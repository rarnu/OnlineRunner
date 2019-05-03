package com.rarnu.code

import com.rarnu.kt.ktor.session
import io.ktor.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext
import java.util.*

data class CodeSession(val uuid: String)

inline val PipelineContext<*, ApplicationCall>.localSession: CodeSession
    get() = session {
        CodeSession(UUID.randomUUID().toString())
    }

