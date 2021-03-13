package com.bapps.kioc.core

import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.factory
import com.bapps.kioc.core.dsl.module
import com.bapps.kioc.core.dsl.single
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ComponentTests {

    @Test
    fun `Component should use all modules to find dependency`() {
        // arrange
        val landVehiclesModule = module {
            single { Car() }
            single { Bike() }
        }
        val seaVehiclesModule = module {
            single { Boat() }
        }
        val garageModule = module(dependsOn = arrayOf(landVehiclesModule, seaVehiclesModule)) {
            factory {
                val car: Car = get()
                val bike: Bike = get()
                val boat: Boat = get()

                Garage(listOf(car, bike, boat))
            }
        }

        // act
        val component = component {
            withModule(seaVehiclesModule)
            withModule(garageModule)
        }

        val car: Car = component.get()
        val bike: Bike = component.get()
        val boat: Boat = component.get()

        // assert
        car.shouldNotBeNull()
        bike.shouldNotBeNull()
        boat.shouldNotBeNull()
    }
}