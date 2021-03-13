package com.bapps.kioc.core

import java.lang.RuntimeException

internal class DependencyRegistry {
    private val dependencies = HashMap<Qualifier, Provider<*>>()

    fun put(qualifier: Qualifier, provider: Provider<*>) {
        if (dependencies.contains(qualifier))
            throw DuplicatedDependencyException(qualifier)

        dependencies[qualifier] = provider
    }

    fun <T> requireDependency(qualifier: Qualifier): T {
        val provider = dependencies[qualifier] ?: throw DependencyNotFoundException(qualifier)
        return provider.get() as T
    }
}

class DependencyNotFoundException(qualifier: Qualifier) :
    RuntimeException("Dependency for qualifier $qualifier not found")

class DuplicatedDependencyException(qualifier: Qualifier) :
    RuntimeException("There is already one dependency registered for qualifier = $qualifier")