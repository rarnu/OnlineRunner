package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class PascalRunner(cmd: String) : CodeIntf(cmd) {

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val ret = RunResult()
        val mainFile = codePack.getValue(start)
        val dir = mainFile.absolutePath.substringBeforeLast("/")
        val dest = mainFile.absolutePath.substringBeforeLast(".")

        runCommand {
            commands.add(cmd)
            commands.add(mainFile.name)
            workDir = dir
            result { out0, _ ->
                if (!out0.contains("Fatal: Compilation aborted")) {
                    runCommand {
                        commands.add(dest)
                        param.forEach { p -> commands.add(p) }
                        result { out1, err1 ->
                            ret.output = out1
                            ret.error = err1
                        }
                    }
                } else {
                    ret.output = ""
                    ret.error = out0
                }
            }
        }
        return ret
    }

    override fun run(codeFile: File, param: List<String>): RunResult {
        val ret = RunResult()
        val dir = codeFile.absolutePath.substringBeforeLast("/")
        val dest = codeFile.absolutePath.substringBeforeLast(".")
        runCommand {
            // compile
            commands.add(cmd)
            commands.add(codeFile.name)
            workDir = dir
            result { out0, _ ->
                if (!out0.contains("Fatal: Compilation aborted")) {
                    runCommand {
                        commands.add(dest)
                        param.forEach { p -> commands.add(p) }
                        result { out1, err1 ->
                            ret.output = out1
                            ret.error = err1
                        }
                    }
                } else {
                    ret.output = ""
                    ret.error = out0
                }
            }
        }
        return ret
    }

}