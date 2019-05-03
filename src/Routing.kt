package com.rarnu.code

import com.rarnu.code.controller.controllers
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post

fun Routing.codeRouting() {

    /**
     * 单个文件执行路由
     */
    post("/code") { call.respondText { call.controllers["/code"]?.control(localSession) ?: "" } }

    /**
     * 多个文件执行路由
     */
    post("/codepack") { call.respondText { call.controllers["/codepack"]?.control(localSession) ?: "" } }

    /**
     * LaTeX 上传图片识别
     */
    post("/latex") { call.respondText { call.controllers["/latex"]?.control(localSession) ?: "" } }

    /**
     * LaTeX 识别（演示用）
     */
    post("/latexsample") { call.respondText { call.controllers["/latexsample"]?.control(localSession) ?: "" } }
}
