package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

data class RunResult(var output: String = "", var error: String = "")

open class CodeIntf(val cmd: String) {

    open fun run(codeFile: File, param: String): RunResult {
        val ret = RunResult()
        val dir = codeFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add(codeFile.name)
            commands.add(param)
            workDir = dir
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }

    open fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val ret = RunResult()
        val dir = codePack.getValue(start).absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add(codePack.getValue(start).name)
            commands.add(param)
            workDir = dir
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }
}
