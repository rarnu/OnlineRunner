package com.rarnu.code.code

import com.rarnu.common.runCommand
import java.io.File

class RustRunner(cmd: String) : CodeIntf(cmd) {

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val ret = RunResult()
        val mainFile = codePack.getValue(start)
        val dir = mainFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(cmd)
            commands.add(mainFile.name)
            workDir = dir
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        commands.add(mainFile.absolutePath.substringBeforeLast("."))
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