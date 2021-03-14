package com.bapps.kioc.core

import java.lang.RuntimeException

class Parameters(vararg params: Any) {
    val parameters = params.toList()

    inline operator fun <reified T> component1() : T = elementAt(0)
    inline operator fun <reified T> component2() : T = elementAt(1)
    inline operator fun <reified T> component3() : T = elementAt(2)
    inline operator fun <reified T> component4() : T = elementAt(3)
    inline operator fun <reified T> component5() : T = elementAt(4)

    inline fun <reified T> elementAt(index: Int) =
        parameters.elementAt(index) as? T ?: throw ParameterNotDefinedException()
}

class ParameterNotDefinedException : RuntimeException("Parameter not defined")