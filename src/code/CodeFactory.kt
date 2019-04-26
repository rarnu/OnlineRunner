package com.rarnu.code.code

import com.rarnu.code.utils.config
import io.ktor.application.ApplicationCall


inline val ApplicationCall.extensions: Map<String, String>
    get() = mapOf(
        "c" to ".c",
        "c++" to ".cpp",
        "javascript" to ".js",
        "python" to ".py",
        "java" to ".java",
        "c#" to ".cs",
        "php" to ".php",
        "objc" to ".m",
        "swift" to ".swift",
        "kotlin" to ".kt",
        "go" to ".go",
        "rust" to ".rust",
        "pascal" to ".pas",
        "ruby" to ".ruby",
        "julia" to ".julia"
    )

inline val ApplicationCall.runners: Map<String, CodeIntf>
    get() = mapOf(
        "c" to CRunner(config("ktor.language.c")),
        "javascript" to JavascriptRunner(config("ktor.language.javascript")),
        "python" to PythonRunner(config("ktor.language.python")),
        "swift" to SwiftRunner(config("ktor.language.swift")),
        "php" to PhpRunner(config("ktor.language.php"))
    )