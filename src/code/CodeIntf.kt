package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

data class RunResult(var output: String = "", var error: String = "")

open class CodeIntf(val cmd: String) {

    open fun run(codeFile: File, param: String): RunResult {
        val ret = RunResult()
        runCommand {
            commands.add(cmd)
            commands.add(codeFile.absolutePath)
            commands.add(param)
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }

    open fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val ret = RunResult()
        runCommand {
            commands.add(cmd)
            commands.add(codePack.getValue(start).absolutePath)
            commands.add(param)
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }
}
