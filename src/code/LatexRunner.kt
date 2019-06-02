package com.rarnu.code.code

import com.rarnu.code.database.LaTeXCache
import com.rarnu.common.runCommand
import com.rarnu.common.md5Sha1
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import org.json.JSONObject
import java.io.File

class LatexRunner(val base64: String, val appid: String, val appkey: String) : CodeIntf("") {

    suspend fun imageToLatex(cache: LaTeXCache ,imgFile: File, format: String): RunResult {
        val md5sha1 = imgFile.md5Sha1
        val latex = cache.find(md5sha1)
        if (latex != "") {
            return RunResult(latex, "")
        }

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
            val locLatex = json.getString("latex")
            if (locLatex.trim() != "") {
                // 识别成功才保存
                cache.save(md5sha1, locLatex)
            }
            RunResult(locLatex, json.getString("error"))
        } catch (th: Throwable) {
            RunResult("", "$th")
        }
    }

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>) = RunResult()
}