package com.rarnu.code.utils

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun makedir(path: String) {
    val f = File(path)
    if (!f.exists()) {
        f.mkdirs()
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.copyToSuspend(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    yieldSize: Int = 4 * 1024 * 1024,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) = withContext(dispatcher) {
    val buffer = ByteArray(bufferSize)
    var bytesCopied = 0L
    var bytesAfterYield = 0L
    while (true) {
        val bytes = read(buffer).takeIf { it >= 0 } ?: break
        out.write(buffer, 0, bytes)
        if (bytesAfterYield >= yieldSize) {
            yield()
            bytesAfterYield %= yieldSize
        }
        bytesCopied += bytes
        bytesAfterYield += bytes
    }
    return@withContext bytesCopied
}