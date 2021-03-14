package com.bapps.kioc.core

class DependencyRegistry {
    private val dependencies = HashMap<Qualifier, Provider<*>>()

    fun put(qualifier: Qualifier, provider: Provider<*>) {
        if (dependencies.contains(qualifier))
            throw DuplicatedDependencyException(qualifier)

        dependencies[qualifier] = provider
    }

    fun <T> get(qualifier: Qualifier): Provider<T>? {
        return dependencies[qualifier] as? Provider<T>
    }
}

