package com.rarnu.code.controller

import com.rarnu.kt.ktor.isMac

object UtilController {
    fun getServerIsMac() = "{\"result\":0, \"ismac\":${if (isMac) 1 else 0}}"
}