package com.rarnu.code.utils

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.content.*
import io.ktor.http.decodeURLPart
import io.ktor.http.defaultForFileExtension
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.normalizeAndRelativize
import java.io.File
import java.nio.file.Paths

fun ApplicationCall.config(cfg: String): String = application.config(cfg)

@UseExperimental(KtorExperimentalAPI::class)
fun ApplicationCall.resolveFileContent (
    path: String,
    resourcePackage: String? = null,
    classLoader: ClassLoader = application.environment.classLoader
): String? {
    val packagePath = (resourcePackage?.replace('.', '/') ?: "").appendPathPart(path)
    val normalizedPath = Paths.get(packagePath).normalizeAndRelativize()
    val normalizedResource = normalizedPath.toString().replace(File.separatorChar, '/')
    for (url in classLoader.getResources(normalizedResource).asSequence()) {
        when (url.protocol) {
            "file" -> {
                val file = File(url.path.decodeURLPart())
                return if (file.isFile) file.readText() else null
            }
        }
    }
    return null
}

@UseExperimental(KtorExperimentalAPI::class)
fun ApplicationCall.resolveFile (
    path: String,
    resourcePackage: String? = null,
    classLoader: ClassLoader = application.environment.classLoader
): File? {
    val packagePath = (resourcePackage?.replace('.', '/') ?: "").appendPathPart(path)
    val normalizedPath = Paths.get(packagePath).normalizeAndRelativize()
    val normalizedResource = normalizedPath.toString().replace(File.separatorChar, '/')
    for (url in classLoader.getResources(normalizedResource).asSequence()) {
        when (url.protocol) {
            "file" -> {
                val file = File(url.path.decodeURLPart())
                return if (file.isFile) file else null
            }
            else -> {
            }
        }
    }
    return null
}


fun ApplicationCall.resourcePath(resourcePackage: String? = null) = application.resourcePath(resourcePackage)