package com.rarnu.code.code

import com.rarnu.kt.common.runCommand
import java.io.File

class CSharpRunner(val csc: String, val mono: String) : CodeIntf("") {

    override fun run(codeFile: File, param: List<String>): RunResult {
        val ret = RunResult()
        val dir = codeFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(csc)
            commands.add(codeFile.name)
            workDir = dir
            result { out0, err0 ->
                if (err0 == "") {
                    runCommand {
                        commands.add(mono)
                        commands.add(codeFile.absolutePath.replace(".cs", ".exe"))
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
        val mainFile = codePack.getValue(start)
        val dir = mainFile.absolutePath.substringBeforeLast("/")
        runCommand {
            commands.add(csc)
            codePack.forEach { _, u -> commands.add(u.name) }
            workDir = dir
            result { out0, err0 ->
                if (err0 == "") {
                    println("main => $mainFile")
                    println("exe => ${mainFile.absolutePath.replace(".cs", ".exe")}")
                    runCommand {
                        commands.add(mono)
                        commands.add(mainFile.absolutePath.replace(".cs", ".exe"))
                        param.forEach { p -> commands.add(p) }
                        workDir = dir
                        result { out1, err1 ->
                            println("out => $out1, err=> $err1")
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