package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*

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

fun <T> ComponentProvider.get(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): T =
    component.get(qualifier, parameters)

inline fun <reified T> ComponentProvider.get(parameters: Parameters = Parameters.EMPTY) =
    component.get<T>(TypeQualifier(T::class), parameters)

inline fun <reified T> ComponentProvider.lazyInjection(parameters: Parameters = Parameters.EMPTY) =
    component.lazyInjection<T>(parameters)

inline fun <reified T> ComponentProvider.lazyInjection(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY) =
    component.lazyInjection<T>(parameters)