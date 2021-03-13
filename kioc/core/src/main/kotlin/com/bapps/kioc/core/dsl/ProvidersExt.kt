package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*

inline fun <reified T> Module.single(qualifier: Qualifier, noinline instanceFactory: InstanceFactory<T>) {
    register(qualifier, Single(instanceFactory))
}

inline fun <reified T> Module.single(noinline instanceFactory: InstanceFactory<T>) {
    register(Single(instanceFactory))
}

inline fun <reified T> Module.factory(qualifier: Qualifier, noinline instanceFactory: InstanceFactory<T>) {
    register(qualifier, Factory(instanceFactory))
}

inline fun <reified T> Module.factory(noinline instanceFactory: InstanceFactory<T>) {
    register(Factory(instanceFactory))
}