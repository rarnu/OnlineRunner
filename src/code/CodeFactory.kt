package com.rarnu.code.code

import com.rarnu.code.ISMACOSX
import com.rarnu.code.utils.config
import io.ktor.application.ApplicationCall


inline val ApplicationCall.extensions: Map<String, String>
    get() = mapOf(
        "c" to ".c",                // done /lin/mac
        "c++" to ".cpp",            // done /lin/mac
        "javascript" to ".js",      // done /lin/mac
        "python" to ".py",          // done /lin/mac
        "java" to ".java",
        "c#" to ".cs",
        "php" to ".php",            // done /lin/mac
        "objc" to ".m",             // done /lin/mac
        "swift" to ".swift",        // done /lin/mac
        "kotlin" to ".kt",
        "go" to ".go",
        "rust" to ".rust",
        "pascal" to ".pas",
        "ruby" to ".ruby",
        "julia" to ".julia",
        "r" to ".r",
        "typescript" to ".ts",
        "scala" to ".scala",
        "haskell" to ".hs",
        "lua" to ".lua",
        "lisp" to ".lisp",
        "perl" to ".perl",
        "basic" to ".bas",
        "dart" to ".dart"

    )

inline val ApplicationCall.runners: Map<String, CodeIntf>
    get() = mapOf(
        "c" to CRunner(config("ktor.language.c")),
        "c++" to CppRunner(config("ktor.language.cpp")),
        "javascript" to JavascriptRunner(config(if (ISMACOSX) "ktor.language.javascriptmac" else "ktor.language.javascript")),
        "python" to PythonRunner(config(if (ISMACOSX) "ktor.language.pythonmac" else "ktor.language.python")),
        "swift" to SwiftRunner(config("ktor.language.swift")),
        "php" to PhpRunner(config("ktor.language.php")),
        "objc" to ObjcRunner(config(if (ISMACOSX) "ktor.language.objcmac" else "ktor.language.objc")),
        "kotlin" to KotlinRunner(config(if (ISMACOSX) "ktor.language.kotlinmac" else "ktor.language.kotlin"))
    )