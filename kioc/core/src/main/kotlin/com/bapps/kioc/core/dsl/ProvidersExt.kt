package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Factory
import com.bapps.kioc.core.InstanceFactory
import com.bapps.kioc.core.Module
import com.bapps.kioc.core.Single

inline fun <reified T> Module.single(noinline instanceFactory: InstanceFactory<T>) {
    register(Single(instanceFactory))
}

inline fun <reified T> Module.factory(noinline instanceFactory: InstanceFactory<T>) {
    register(Factory(instanceFactory))
}