package com.rarnu.code.code

import java.io.File

class PythonRunner(cmd: String) : CodeIntf(cmd) {
    override fun runPack(codePack: Map<String, File>, start: String): RunResult {
        // TODO: run pack
        return RunResult()
    }
}