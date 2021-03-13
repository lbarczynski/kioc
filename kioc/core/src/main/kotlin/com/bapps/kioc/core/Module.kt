package com.bapps.kioc.core

class Module(private val dependsOn: List<Module> = emptyList()) {

    private val dependencyRegistry = DependencyRegistry()

    fun register(qualifier: Qualifier, provider: Provider<*>) {
        dependencyRegistry.put(qualifier, provider)
    }

    fun <T> get(qualifier: Qualifier): T? {
        return dependencyRegistry.get<T>(qualifier) ?: dependsOn.map { it.get<T>(qualifier) }.firstOrNull()
    }

    fun <T> require(qualifier: Qualifier): T {
        return get<T>(qualifier) ?: throw DependencyNotFoundException(qualifier)
    }

    inline fun <reified T> register(provider: Provider<T>) = register(TypeQualifier(T::class), provider)
    inline fun <reified T> get() = get<T>(TypeQualifier(T::class))
    inline fun <reified T> require() = require<T>(TypeQualifier(T::class))
}

class ModuleScope(private val module: Module) {
    fun <T> get(qualifier: Qualifier) = module.get<T>(qualifier)
}