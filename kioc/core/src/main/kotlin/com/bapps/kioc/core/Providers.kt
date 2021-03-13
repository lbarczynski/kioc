package com.bapps.kioc.core

typealias InstanceFactory<T> = ModuleScope.() -> T

interface Provider<out T> {
    fun get(): T
}

class Single<T>(private val moduleScope: ModuleScope, factory: InstanceFactory<T>) : Provider<T> {

    private val instance by lazy {
        factory(moduleScope)
    }

    override fun get(): T {
        return instance
    }
}

class Factory<T>(private val moduleScope: ModuleScope, private val factory: InstanceFactory<T>) : Provider<T> {
    override fun get(): T {
        return factory(moduleScope)
    }
}