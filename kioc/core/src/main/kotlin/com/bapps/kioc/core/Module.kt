package com.bapps.kioc.core

import kotlin.reflect.KClass

private class DependencyRegistry {
    private val dependencies = HashMap<KClass<*>, Provider<*>>()

    fun put(clazz: KClass<*>, provider: Provider<*>) {
        dependencies[clazz] = provider
    }

    fun <T> requireDependency(clazz: KClass<*>): T {
        // TODO : Throw dedicated exception if not found or type is valid
        return dependencies[clazz]!!.get() as T
    }
}

class Module(/*vararg dependsOn: Module*/) {

    private val dependencyRegistry = DependencyRegistry()
//    private val dependentModules = listOf(dependsOn)

    fun register(clazz: KClass<*>, provider: Provider<*>) {
        dependencyRegistry.put(clazz, provider)
    }

    fun <T> get(clazz: KClass<*>): T {
        return dependencyRegistry.requireDependency(clazz)
    }

    inline fun <reified T> register(provider: Provider<T>) = register(T::class, provider)

    inline fun <reified T> get() = get<T>(T::class)
}