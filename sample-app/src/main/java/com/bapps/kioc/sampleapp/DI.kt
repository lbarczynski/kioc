package com.bapps.kioc.sampleapp

import com.bapps.kioc.androidx.viewModel
import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.factory
import com.bapps.kioc.core.dsl.module
import com.bapps.kioc.core.dsl.singleton

class SimpleClass {
    override fun toString(): String {
        return "SimpleClass: ${hashCode()}"
    }
}

class ComplexClass(val value: String) {
    override fun toString(): String {
        return "ComplexClass: ${hashCode()}"
    }
}

object DI {

    private val mainModule = module {
        singleton { SimpleClass() }
        factory { (value: String) -> ComplexClass(value) }
        viewModel { (text: String) ->
            MainViewModel(text)
        }
    }

    val container = component {
        withModule(mainModule)
    }
}