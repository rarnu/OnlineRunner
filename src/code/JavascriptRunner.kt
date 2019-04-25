package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class JavascriptRunner: CodeIntf {
    override fun runPack(cmd: String, codePack: Map<String, File>, start: String): RunResult {
        val ret = RunResult()
        runCommand {
            commands.add(cmd)
            commands.add(codePack.getValue(start).absolutePath)
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }

    override fun run(cmd: String, codeFile: File): RunResult {
        val ret = RunResult()
        runCommand {
            commands.add(cmd)
            commands.add(codeFile.absolutePath)
            result { output, error ->
                ret.output = output
                ret.error = error
            }
        }
        return ret
    }
}