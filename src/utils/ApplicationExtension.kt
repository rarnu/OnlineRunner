package com.rarnu.code.utils

import io.ktor.application.Application
import io.ktor.util.KtorExperimentalAPI

@UseExperimental(KtorExperimentalAPI::class)
fun Application.config(key: String) = environment.config.property(key).getString()

fun Application.mkdir(path: String) = makedir(path)
