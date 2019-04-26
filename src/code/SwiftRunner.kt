package com.rarnu.code.code

import java.io.File

class SwiftRunner(cmd: String) : CodeIntf(cmd) {

    override fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val mainFile = codePack.getValue(start)
        val projPath = mainFile.absolutePath.substringBeforeLast("/")
        var codeStr = ""
        for ((k, v) in codePack) { if (k != start) { codeStr += "${v.readText()}\n" } }
        codeStr += "${mainFile.readText()}\n"
        val dest = File(projPath, "dest.swift")
        dest.writeText(codeStr)
        return run(dest, param)
    }

}