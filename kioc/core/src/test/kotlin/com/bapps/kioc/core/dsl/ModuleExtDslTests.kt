package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*
import org.amshove.kluent.shouldContain
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
                val car: Car = require()
                val bike: Bike = require()
                val boat: Boat = require()
                Garage(listOf(car, bike, boat))
            }
        }
        val garage: Garage = module.require()

        // assert
        garage.vehicles shouldContain car
        garage.vehicles shouldContain bike
        garage.vehicles shouldContain boat
    }

    @Test
    fun `Related modules should be used to return dependency`() {
        // arrange
        val car = Car()
        val bike = Bike()

        // act
        val vehiclesModule = module {
            single { car }
            single { bike }
        }

        val garageModule = module(dependsOn = arrayOf(vehiclesModule)) {
            single {
                val car: Car = require()
                val bike: Bike = require()
                Garage(listOf(car, bike))
            }
        }
        val garage: Garage = garageModule.require()

        // assert
        garage.vehicles shouldContain car
        garage.vehicles shouldContain bike
    }
}