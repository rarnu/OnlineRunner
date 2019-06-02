package com.rarnu.code.code

import com.rarnu.common.runCommand
import java.io.File

class SwiftRunner(cmd: String) : CodeIntf(cmd) {

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val mainFile = codePack.getValue(start)
        val projPath = mainFile.absolutePath.substringBeforeLast("/")
        var codeStr = ""
        for ((k, v) in codePack) { if (k != start) { codeStr += "${v.readText()}\n" } }
        codeStr += "${mainFile.readText()}\n"
        val dest = File(projPath, "dest.swift")
        dest.writeText(codeStr)
        val dir = dest.absolutePath.substringBeforeLast("/")
        val ret = RunResult()
        runCommand {
            commands.add(cmd)
            commands.add(dest.name)
            param.forEach { p -> commands.add(p) }
            workDir = dir
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }

}