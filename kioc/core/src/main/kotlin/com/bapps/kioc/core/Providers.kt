package com.bapps.kioc.core

typealias InstanceFactory<T> = () -> T

interface Provider<out T> {
    fun get(): T
}

class Single<T>(factory: InstanceFactory<T>) : Provider<T> {

    private val instance by lazy {
        factory()
    }

    override fun get(): T {
        return instance
    }
}

class Factory<T>(private val factory: InstanceFactory<T>) : Provider<T> {
    override fun get(): T {
        return factory()
    }
}