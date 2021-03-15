package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.*
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldContain
import org.junit.Test

class ModuleExtDslTests {

    @Test
    fun `Module factory test`() {
        module {
            singleton { Car() }
            factory<Vehicle> { Bike() }
            singleton(named("secondCar")) { Car() }
        }
    }

    @Test
    fun `Related dependencies should be available during registration`() {
        // arrange
        val car = Car()
        val bike = Bike()
        val boat = Boat()

        // act
        val module = module {
            singleton { car }
            singleton { bike }
            singleton { boat }

            factory {
                val car: Car = get()
                val bike: Bike = get()
                val boat: Boat = get()
                Garage(listOf(car, bike, boat))
            }
        }
        val garage: Garage = module
            .require<Garage>()
            .get()

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
            singleton { car }
            singleton { bike }
        }

        val garageModule = module(dependsOn = arrayOf(vehiclesModule)) {
            singleton {
                val car: Car = get()
                val bike: Bike = get()
                Garage(listOf(car, bike))
            }
        }
        val garage: Garage = garageModule
            .require<Garage>()
            .get()

        // assert
        garage.vehicles shouldContain car
        garage.vehicles shouldContain bike
    }

    @Test
    fun `Provider parameters should be used to create dependency`() {
        // arrange
        val expectedWheelsValue = 10
        val module = module {
            factory<Vehicle> { (wheels: Int) ->
                Car(wheels)
            }
        }

        // act
        val vehicle = module
            .require<Vehicle>()
            .get(Parameters.of(expectedWheelsValue))

        // assert
        vehicle.wheels `should be equal to` expectedWheelsValue
    }
}