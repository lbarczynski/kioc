package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Component
import com.bapps.kioc.core.Module

typealias ComponentFactory = Component.Builder.() -> Unit

fun component(factory: ComponentFactory): Component {
    val builder = Component.Builder()
    factory(builder)
    return builder.build()
}

fun Component.Builder.module(moduleFactory: ModuleFactory) {
    val module = Module()
    moduleFactory(module)
    withModule(module)
}