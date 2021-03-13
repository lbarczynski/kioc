package com.bapps.kioc.core

import org.junit.Test

class ModuleTests {

    @Test
    fun `Module registration test`() {
        // arrange
        val module = Module()

        // act
        module.register<Vehicle>(Single { Car() })
        module.register(Single { Car() })

        // assert
        val vehicle = module.get<Vehicle>()
        val car = module.get<Car>()
        println("vehicle = ${vehicle.hashCode()}")
        println("car = ${car.hashCode()}")
    }
}