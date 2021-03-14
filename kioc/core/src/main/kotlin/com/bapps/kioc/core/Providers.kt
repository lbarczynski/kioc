package com.bapps.kioc.core

typealias InstanceFactory<T> = ModuleScope.(Parameters) -> T

interface Provider<out T> {
    fun get(parameters: Parameters = Parameters()): T
}

class Singleton<T>(private val moduleScope: ModuleScope, private val factory: InstanceFactory<T>) : Provider<T> {

    private var instance: T? = null

    override fun get(parameters: Parameters): T {
        if (instance == null)
            instance = factory(moduleScope, parameters)
        return instance!!
    }
}

class Factory<T>(private val moduleScope: ModuleScope, private val factory: InstanceFactory<T>) : Provider<T> {
    override fun get(parameters: Parameters): T {
        return factory(moduleScope, parameters)
    }
}