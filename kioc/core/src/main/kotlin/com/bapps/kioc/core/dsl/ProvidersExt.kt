package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*

inline fun <reified T> Module.single(qualifier: Qualifier, noinline instanceFactory: InstanceFactory<T>) {
    register(qualifier, Single(ModuleScope(this), instanceFactory))
}

inline fun <reified T> Module.single(noinline instanceFactory: InstanceFactory<T>) {
    register(Single(ModuleScope(this), instanceFactory))
}

inline fun <reified T> Module.factory(qualifier: Qualifier, noinline instanceFactory: InstanceFactory<T>) {
    register(qualifier, Factory(ModuleScope(this), instanceFactory))
}

inline fun <reified T> Module.factory(noinline instanceFactory: InstanceFactory<T>) {
    register(Factory(ModuleScope(this), instanceFactory))
}