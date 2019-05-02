package com.rarnu.code.utils

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest

val File.md5Sha1: String get() = hashFile("MD5") + hashFile("SHA1")

private fun File.hashFile(alg: String): String {
    if (!this.isFile) {
        return ""
    }
    val digest = MessageDigest.getInstance(alg)
    val ins = FileInputStream(this)
    val buffer = ByteArray(1024);
    while (true) {
        val len = ins.read(buffer, 0, 1024)
        if (len == -1) break
        digest.update(buffer, 0, len)
    }
    ins.close()
    val bigInt = BigInteger(1, digest.digest())
    return bigInt.toString(16)
}
