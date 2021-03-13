package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Module

fun module(moduleFactory: Module.() -> Unit): Module {
    val module = Module()
    moduleFactory(module)
    return module
}