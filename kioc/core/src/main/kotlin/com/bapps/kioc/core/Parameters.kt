package com.bapps.kioc.core

import java.lang.RuntimeException

open class Parameters(val parameters: Array<out Any>) {

    inline operator fun <reified T> component1(): T = elementAt(0)
    inline operator fun <reified T> component2(): T = elementAt(1)
    inline operator fun <reified T> component3(): T = elementAt(2)
    inline operator fun <reified T> component4(): T = elementAt(3)
    inline operator fun <reified T> component5(): T = elementAt(4)

    inline fun <reified T> elementAt(index: Int) =
        parameters.elementAt(index) as? T ?: throw ParameterNotDefinedException()
}

fun parameters(vararg parameters: Any) = Parameters(parameters)

class ParameterNotDefinedException : RuntimeException("Parameter not defined")