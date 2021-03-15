package com.bapps.kioc.core

import java.lang.RuntimeException

open class Parameters(val parameters: Array<out Any>) {

    inline operator fun <reified T> component1(): T = requireAt(0)
    inline operator fun <reified T> component2(): T = requireAt(1)
    inline operator fun <reified T> component3(): T = requireAt(2)
    inline operator fun <reified T> component4(): T = requireAt(3)
    inline operator fun <reified T> component5(): T = requireAt(4)

    inline fun <reified T> requireAt(index: Int) = parameters.elementAt(index) as? T ?: throw ParameterNotDefinedException()

    companion object {
        val EMPTY = Parameters(emptyArray())
        fun of(vararg parameters: Any) = Parameters(parameters)
    }
}

class ParameterNotDefinedException : RuntimeException("Parameter not defined")