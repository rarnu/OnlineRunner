package com.rarnu.code.code

import com.rarnu.code.ISMACOSX
import com.rarnu.code.utils.config
import io.ktor.application.ApplicationCall


inline val ApplicationCall.extensions: Map<String, String>
    get() = mapOf(
        "c" to ".c",                // /lin/mac
        "c++" to ".cpp",            // /lin/mac
        "javascript" to ".js",      // /lin/mac
        "python" to ".py",          // /lin/mac
        "java" to ".java",          // /lin/mac
        "c#" to ".cs",
        "php" to ".php",            // /lin/mac
        "objc" to ".m",             // /lin/mac
        "swift" to ".swift",        // /lin/mac
        "kotlin" to ".kt",          // /lin/mac
        "go" to ".go",              // /lin/mac
        "rust" to ".rust",
        "pascal" to ".pas",
        "ruby" to ".rb",            // /lin/mac
        "julia" to ".julia",
        "r" to ".r",                // /lin/mac
        "typescript" to ".ts",      // /lin/mac
        "scala" to ".scala",        // /lin/mac
        "haskell" to ".hs",
        "lua" to ".lua",            // /lin/mac
        "perl" to ".pl",            // /lin/mac
        "basic" to ".bas",
        "dart" to ".dart"           // /lin
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
        "kotlin" to KotlinRunner(config(if (ISMACOSX) "ktor.language.kotlinmac" else "ktor.language.kotlin")),
        "java" to JavaRunner(config("ktor.language.java")),
        "go" to GoRunner(config(if (ISMACOSX) "ktor.language.gomac" else "ktor.language.go")),
        "typescript" to TypescriptRunner(config("ktor.language.typescript"), config(if (ISMACOSX) "ktor.language.javascriptmac" else "ktor.language.javascript")),
        "dart" to DartRunner(config("ktor.language.dart")),
        "ruby" to RubyRunner(config("ktor.language.ruby")),
        "perl" to PerlRunner(config("ktor.language.perl")),
        "lua" to LuaRunner(config(if (ISMACOSX) "ktor.language.luamac" else "ktor.language.lua")),
        "scala" to ScalaRunner(config(if (ISMACOSX) "ktor.language.scalamac" else "ktor.language.scala")),
        "r" to RRunner(config(if (ISMACOSX) "ktor.language.Rmac" else "ktor.language.R")),

        "latex" to LatexRunner(config("ktor.mathpix.base64"), config("ktor.mathpix.appid"), config("ktor.mathpix.appkey"))
    )