package com.rarnu.code.code

import com.rarnu.kt.ktor.config
import com.rarnu.kt.ktor.ifcfg
import com.rarnu.kt.ktor.isMac
import io.ktor.application.ApplicationCall

inline val ApplicationCall.extensions: Map<String, String>
    get() = mapOf(
        "c" to ".c",                // /lin/mac
        "c++" to ".cpp",            // /lin/mac
        "javascript" to ".js",      // /lin/mac
        "python" to ".py",          // /lin/mac
        "java" to ".java",          // /lin/mac
        "c#" to ".cs",              // /lin/mac
        "php" to ".php",            // /lin/mac
        "objc" to ".m",             // /lin/mac
        "swift" to ".swift",        // /lin/mac
        "kotlin" to ".kt",          // /lin/mac
        "go" to ".go",              // /lin/mac
        "rust" to ".rs",            // /lin/mac
        "pascal" to ".pas",         // /lin/mac
        "ruby" to ".rb",            // /lin/mac
        "julia" to ".jl",           // /lin/mac
        "r" to ".r",                // /lin/mac
        "typescript" to ".ts",      // /lin/mac
        "scala" to ".scala",        // /lin/mac
        "lua" to ".lua",            // /lin/mac
        "perl" to ".pl",            // /lin/mac
        "dart" to ".dart",          // /lin/mac
        "f#" to ".fs"               // /lin/mac

        // latex                    // /api
    )

inline val ApplicationCall.runners: Map<String, CodeIntf>
    get() = mapOf(
        "c" to CRunner(config("ktor.language.c")),
        "c++" to CppRunner(config("ktor.language.cpp")),
        "javascript" to JavascriptRunner(ifcfg(isMac, "ktor.language.javascriptmac", "ktor.language.javascript")),
        "python" to PythonRunner(ifcfg(isMac, "ktor.language.pythonmac", "ktor.language.python")),
        "swift" to SwiftRunner(config("ktor.language.swift")),
        "php" to PhpRunner(config("ktor.language.php")),
        "objc" to ObjcRunner(ifcfg(isMac, "ktor.language.objcmac", "ktor.language.objc")),
        "kotlin" to KotlinRunner(ifcfg(isMac, "ktor.language.kotlinmac", "ktor.language.kotlin")),
        "java" to JavaRunner(config("ktor.language.java")),
        "go" to GoRunner(ifcfg(isMac, "ktor.language.gomac", "ktor.language.go")),
        "typescript" to TypescriptRunner(config("ktor.language.typescript"), ifcfg(isMac, "ktor.language.javascriptmac", "ktor.language.javascript")),
        "dart" to DartRunner(ifcfg(isMac, "ktor.language.dartmac", "ktor.language.dart")),
        "ruby" to RubyRunner(config("ktor.language.ruby")),
        "perl" to PerlRunner(config("ktor.language.perl")),
        "lua" to LuaRunner(ifcfg(isMac, "ktor.language.luamac", "ktor.language.lua")),
        "scala" to ScalaRunner(ifcfg(isMac, "ktor.language.scalamac", "ktor.language.scala")),
        "r" to RRunner(ifcfg(isMac, "ktor.language.Rmac", "ktor.language.R")),
        "julia" to JuliaRunner(config("ktor.language.julia")),
        "rust" to RustRunner(ifcfg(isMac, "ktor.language.rustmac", "ktor.language.rust")),
        "c#" to CSharpRunner(ifcfg(isMac, "ktor.language.csharpmac", "ktor.language.csharp"), ifcfg(isMac, "ktor.language.netrunmac", "ktor.language.netrun")),
        "f#" to FSharpRunner(ifcfg(isMac, "ktor.language.fsharpmac", "ktor.language.fsharp"), ifcfg(isMac, "ktor.language.netrunmac", "ktor.language.netrun")),
        "pascal" to PascalRunner(ifcfg(isMac, "ktor.language.pascalmac", "ktor.language.pascal")),
        "latex" to LatexRunner(config("ktor.mathpix.base64"), config("ktor.mathpix.appid"), config("ktor.mathpix.appkey"))
    )