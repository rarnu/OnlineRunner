package com.rarnu.code.utils

import io.ktor.application.ApplicationCall

fun ApplicationCall.config(cfg: String): String = application.config(cfg)