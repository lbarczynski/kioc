package com.bapps.kioc.core

class Component(private val modules: List<Module>) {

    fun <T> get(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): T {
        val found = modules.mapNotNull { it.get<T>(qualifier) }
        if (found.size > 1) throw DuplicatedDependencyException(qualifier)
        val provider = found.firstOrNull() ?: throw DependencyNotFoundException(qualifier)
        return provider.get(parameters)
    }

    inline fun <reified T> get(parameters: Parameters = Parameters.EMPTY) = get<T>(TypeQualifier(T::class), parameters)

    inline fun <reified T> lazyInjection(parameters: Parameters = Parameters.EMPTY) = lazy { get<T>(TypeQualifier(T::class), parameters) }

    class Builder {
        private val modules = mutableListOf<Module>()

        fun withModule(module: Module): Builder {
            modules.add(module)
            return this
        }

        fun withModules(modules: Array<Module>): Builder {
            this.modules.addAll(modules)
            return this
        }

        fun build() = Component(modules)
    }
}