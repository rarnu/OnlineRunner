package com.rarnu.code.controller

import com.rarnu.common.isMac

object UtilController {
    fun getServerIsMac() = "{\"result\":0, \"ismac\":${if (isMac) 1 else 0}}"
}