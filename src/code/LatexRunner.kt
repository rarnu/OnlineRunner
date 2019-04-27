package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import java.io.File

class LatexRunner(val base64: String, val appid: String, val appkey: String) : CodeIntf("") {

    suspend fun imageToLatex(imgFile: File, format: String): RunResult {
        var b64img = ""
        runCommand {
            commands.add(base64)
            commands.add("-i")
            commands.add(imgFile.absolutePath)
            result { output, _ ->
                b64img = output
            }
        }
        val client = HttpClient(Apache) {
            engine {
                followRedirects = true
                socketTimeout = 30_000
                connectTimeout = 30_000
                connectionRequestTimeout = 30_000
            }
        }
        return try {
            val jsonstr = client.post<String>("https://api.mathpix.com/v3/latex") {
                header("app_id", appid)
                header("app_key", appkey)
                body = TextContent("{\"src\":\"data:image/$format;base64,'$b64img'\" }", ContentType.parse("application/json"))
            }
            val json = JSONObject(jsonstr)
            RunResult(json.getString("latex"), json.getString("error"))
        } catch (th: Throwable) {
            RunResult("", "$th")
        }

    }

    override fun run(codeFile: File, param: String) = RunResult()
    override fun runPack(codePack: Map<String, File>, start: String, param: String) = RunResult()
}