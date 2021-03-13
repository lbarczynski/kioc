package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Component

typealias ComponentFactory = Component.Builder.() -> Unit

fun component(factory: ComponentFactory): Component {
    val builder = Component.Builder()
    factory(builder)
    return builder.build()
}