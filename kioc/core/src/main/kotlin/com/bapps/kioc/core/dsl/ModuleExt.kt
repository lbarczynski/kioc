package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Module

typealias ModuleFactory = Module.() -> Unit

fun module(scope: ModuleFactory) = module(emptyArray(), scope)

fun module(dependsOn: Array<Module>, factory: ModuleFactory): Module {
    val module = Module(dependsOn.toList())
    factory(module)
    return module
}