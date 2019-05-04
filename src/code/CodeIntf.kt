package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

data class RunResult(var output: String = "", var error: String = "")

open class CodeIntf(val cmd: String) {

    open fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val ret = RunResult()
        val dir = codePack.getValue(start).absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add(codePack.getValue(start).name)
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
