package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class GoRunner(cmd: String) : CodeIntf(cmd) {

    override fun run(codeFile: File, param: String): RunResult {
        val ret = RunResult()
        val dir = codeFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add("run")
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

    override fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val ret = RunResult()
        val dir = codePack.getValue(start).absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add("run")
            codePack.filterKeys { it.endsWith(".go") }.forEach { _, u -> commands.add(u.name) }
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