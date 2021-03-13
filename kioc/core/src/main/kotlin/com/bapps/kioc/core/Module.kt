package com.bapps.kioc.core

class Module(/*vararg dependsOn: Module*/) {

    private val dependencyRegistry = DependencyRegistry()
//    private val dependentModules = listOf(dependsOn)

    fun register(qualifier: Qualifier, provider: Provider<*>) {
        dependencyRegistry.put(qualifier, provider)
    }

    fun <T> get(qualifier: Qualifier): T {
        return dependencyRegistry.requireDependency(qualifier)
    }

    inline fun <reified T> register(provider: Provider<T>) = register(TypeQualifier(T::class), provider)
    inline fun <reified T> get() = get<T>(TypeQualifier(T::class))
}

class ModuleScope(private val module: Module) {
    fun <T> get(qualifier: Qualifier) = module.get<T>(qualifier)
}
