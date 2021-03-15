package com.bapps.kioc.core

class Module(private val dependsOn: List<Module> = emptyList()) {

    private val dependencyRegistry = DependencyRegistry()

    fun register(qualifier: Qualifier, provider: Provider<*>) {
        dependencyRegistry.put(qualifier, provider)
    }

    fun <T> get(qualifier: Qualifier): Provider<T>? {
        return dependencyRegistry.get(qualifier) ?: dependsOn.mapNotNull { it.get<T>(qualifier) }.firstOrNull()
    }

    fun <T> require(qualifier: Qualifier): Provider<T> {
        return get(qualifier) ?: throw DependencyNotFoundException(qualifier)
    }

    inline fun <reified T> register(provider: Provider<T>) = register(TypeQualifier(T::class), provider)
    inline fun <reified T> get() = get<T>(TypeQualifier(T::class))
    inline fun <reified T> require() = require<T>(TypeQualifier(T::class))
}

class ModuleScope(private val module: Module) {

    fun <T> get(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY) =
        module.require<T>(qualifier).get(parameters)

    inline fun <reified T> get(parameters: Parameters = Parameters.EMPTY) =
        get<T>(TypeQualifier(T::class), parameters)
}