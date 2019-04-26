package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class CRunner(cmd: String) : CodeIntf(cmd) {
    override fun runPack(codePack: Map<String, File>, start: String): RunResult {
        // TODO: run pack
        return RunResult()
    }

    override fun run(codeFile: File): RunResult {
        val ret = RunResult()
        val dest = codeFile.absolutePath.replace(".c", ".out")
        runCommand {
            // compile
            commands.add(cmd)
            commands.add("-w")
            commands.add("-o")
            commands.add(dest)
            commands.add(codeFile.absolutePath)
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        // run compile result
                        commands.add(dest)
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