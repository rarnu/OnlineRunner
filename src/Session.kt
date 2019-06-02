package com.rarnu.code

import io.ktor.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext
import java.util.*
import com.rarnu.ktor.session

data class CodeSession(val uuid: String)

inline val PipelineContext<*, ApplicationCall>.localSession: CodeSession
    get() = session {
        CodeSession(UUID.randomUUID().toString())
    }

