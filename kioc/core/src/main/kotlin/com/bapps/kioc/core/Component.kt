package com.bapps.kioc.core

class Component(private val modules: List<Module>) {

    fun <T> get(qualifier: Qualifier): T {
        val found = modules.mapNotNull { it.get<T>(qualifier) }
        if (found.size > 1) throw DuplicatedDependencyException(qualifier)
        return found.firstOrNull() ?: throw DependencyNotFoundException(qualifier)
    }

    inline fun <reified T> get() = get<T>(TypeQualifier(T::class))

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