package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class FSharpRunner(val fsc: String, val mono: String) : CodeIntf("") {

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val ret = RunResult()
        val mainFile = codePack.getValue(start)
        val dir = mainFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(fsc)
            codePack.filterKeys { it != start }.forEach { _, u -> commands.add(u.name) }
            commands.add(start)
            workDir = dir
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        commands.add(mono)
                        commands.add(mainFile.absolutePath.replace(".fs", ".exe"))
                        param.forEach { p -> commands.add(p) }
                        workDir = dir
                        result { out1, err1 ->
                            ret.output = out1
                            ret.error = err1
                        }
                    }
                } else {
                    ret.output = out0
                    ret.error = err0
                }
            }
        }
        return ret
    }
}