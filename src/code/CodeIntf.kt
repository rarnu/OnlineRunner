package com.rarnu.code.code

import java.io.File

data class RunResult(var output: String = "", var error: String = "")

interface CodeIntf {
    fun run(cmd: String, codeFile: File): RunResult
    fun runPack(cmd: String, codePack: Map<String, File>, start: String): RunResult
}
