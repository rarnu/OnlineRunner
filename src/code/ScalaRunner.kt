package com.rarnu.code.code

import com.rarnu.code.utils.proj
import com.rarnu.kt.common.runCommand
import java.io.File

class ScalaRunner(cmd: String) : CodeIntf(cmd) {

    override fun run(codeFile: File, param: List<String>): RunResult {
        val ret = RunResult()
        val dest = codeFile.nameWithoutExtension.proj()
        val dir = codeFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add("${cmd}c")
            commands.add(codeFile.name)
            workDir = dir
            timeout = 5000
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        commands.add(cmd)
                        commands.add(dest)
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

    override fun runPack(codePack: Map<String, File>, start: String, param: List<String>): RunResult {
        val ret = RunResult()
        val dest = codePack.getValue(start).nameWithoutExtension.proj()
        val dir = codePack.getValue(start).absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add("${cmd}c")
            codePack.filterKeys { it.endsWith(".scala") }.forEach { _, u -> commands.add(u.name) }
            workDir = dir
            timeout = 5000
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        commands.add(cmd)
                        commands.add(dest)
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