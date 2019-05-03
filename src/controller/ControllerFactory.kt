package com.rarnu.code.controller

import io.ktor.application.ApplicationCall

inline val ApplicationCall.controllers: Map<String, ControllerIntf>
    get() = mapOf(
        "/code" to CodeController(this),
        "/codepack" to CodePackController(this),
        "/latex" to LatexController(this),
        "/latexsample" to LatexSampleController(this)
    )