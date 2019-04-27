package com.rarnu.code.utils

import io.ktor.application.Application
import io.ktor.http.decodeURLPart
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.normalizeAndRelativize
import java.io.File
import java.nio.file.Paths

@UseExperimental(KtorExperimentalAPI::class)
fun Application.config(key: String) = environment.config.property(key).getString()

fun Application.mkdir(path: String) = makedir(path)

@UseExperimental(KtorExperimentalAPI::class)
fun Application.resourcePath(resourcePackage: String? = null): File? {
    val packagePath = (resourcePackage?.replace('.', '/') ?: "")
    val normalizedPath = Paths.get(packagePath).normalizeAndRelativize()
    val normalizedResource = normalizedPath.toString().replace(File.separatorChar, '/')
    for (url in environment.classLoader.getResources(normalizedResource).asSequence()) {
        when (url.protocol) {
            "file" -> {
                val file = File(url.path.decodeURLPart())
                return if (file.isDirectory) file else null
            }
        }
    }
    return null
}