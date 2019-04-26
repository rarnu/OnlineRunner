package com.rarnu.code.code

import com.rarnu.code.ISMACOSX
import com.rarnu.kt.common.runCommand
import java.io.File

class ObjcRunner(cmd: String) : CodeIntf(cmd) {
    override fun runPack(codePack: Map<String, File>, start: String, param: String): RunResult {
        val ret = RunResult()
        val mainDest = codePack.getValue(start).absolutePath.replace(".m", ".out")
        runCommand {
            commands.add(cmd)
            commands.add("-w")
            commands.add("-o")
            commands.add(mainDest)
            codePack.filterKeys { it.endsWith(".m") }.forEach { _, u -> commands.add(u.absolutePath) }
            if (ISMACOSX) {
                commands.add("-fobjc-arc")
                commands.add("-framework")
                commands.add("Foundation")
            } else {
                commands.add("-fconstant-string-class=NSConstantString")
                commands.add("-I")
                commands.add("/GNUstep/System/Library/Headers/")
                commands.add("-I/usr/local/include/GNUstep")
                commands.add("-I/usr/include/GNUstep")
                commands.add("-I.")
                commands.add("-L")
                commands.add("/GNUstep/System/Library/Libraries/")
                commands.add("-lobjc")
                commands.add("-lgnustep-base")
                commands.add("-fgnu-runtime")
                commands.add("-pthread")
            }

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
        val dest = codeFile.absolutePath.replace(".m", ".out")
        runCommand {
            // compile
            commands.add(cmd)
            commands.add("-w")
            commands.add("-o")
            commands.add(dest)
            commands.add(codeFile.absolutePath)
            if (ISMACOSX) {
                commands.add("-fobjc-arc")
                commands.add("-framework")
                commands.add("Foundation")
            } else {
                commands.add("-fconstant-string-class=NSConstantString")
                commands.add("-I")
                commands.add("/GNUstep/System/Library/Headers/")
                commands.add("-I/usr/local/include/GNUstep")
                commands.add("-I/usr/include/GNUstep")
                commands.add("-I.")
                commands.add("-L")
                commands.add("/GNUstep/System/Library/Libraries/")
                commands.add("-lobjc")
                commands.add("-lgnustep-base")
                commands.add("-fgnu-runtime")
                commands.add("-pthread")
            }
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