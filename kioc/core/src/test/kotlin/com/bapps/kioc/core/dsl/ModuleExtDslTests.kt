package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAll
import org.junit.Test

class ModuleExtDslTests {

    @Test
    fun `Module factory test`() {
        module {
            single { Car() }
            factory<Vehicle> { Bike() }
            single(named("secondCar")) { Car() }
        }
    }

    @Test
    fun `Related dependencies should be possible to register`() {
        // arrange
        val car = Car()
        val bike = Bike()
        val boat = Boat()

        // act
        val module = module {
            single { car }
            single { bike }
            single { boat }

            factory {
                val car: Car = get()
                val bike: Bike = get()
                val boat: Boat = get()
                Garage(listOf(car, bike, boat))
            }
        }
        val garage: Garage = module.get()

        // assert
        garage.vehicles shouldContain car
        garage.vehicles shouldContain bike
        garage.vehicles shouldContain boat
    }
}