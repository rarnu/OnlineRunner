package com.rarnu.code.database

import com.rarnu.kt.ktor.conn
import com.rarnu.kt.ktor.firstRecord
import com.rarnu.kt.ktor.string
import io.ktor.application.Application
import java.util.*

class LaTeXCache(val app: Application) {

    fun save(md5sha1: String, latex: String) {
        val uuid = UUID.randomUUID().toString()
        with(app.conn.prepareStatement("insert into LaTeXCache(uuid, md5sha1, latex) values (?, ?, ?)")) {
            setString(1, uuid)
            setString(2, md5sha1)
            setString(3, latex)
            executeUpdate()
            close()
        }
    }

    fun find(md5sha1: String): String {
        var ret = ""
        with(app.conn.prepareStatement("select latex from LaTeXCache where md5sha1 = ?")) {
            setString(1, md5sha1)
            val rs = executeQuery()
            rs?.firstRecord { ret = it.string("latex") }
            rs?.close()
            close()
        }
        return ret
    }
}

inline val Application.latex: LaTeXCache get() = LaTeXCache(this)