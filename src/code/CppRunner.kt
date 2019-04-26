package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class CppRunner(cmd: String) : CodeIntf(cmd) {
    override fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val ret = RunResult()
        val mainDest = codePack.getValue(start).absolutePath.replace(".cpp", ".out")
        runCommand {
            commands.add(cmd)
            commands.add("-w")
            commands.add("-o")
            commands.add(mainDest)
            codePack.filterKeys { it.endsWith(".cpp") }.forEach { _, u -> commands.add(u.absolutePath) }
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        // run compile result
                        commands.add(mainDest)
                        commands.add(param)
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

    override fun run(codeFile: File, param: String): RunResult {
        val ret = RunResult()
        val dest = codeFile.absolutePath.replace(".cpp", ".out")
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
                        commands.add(param)
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