package com.bapps.kioc.core

import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.factory
import com.bapps.kioc.core.dsl.module
import com.bapps.kioc.core.dsl.singleton
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ComponentTests {

    @Test
    fun `Component should use all modules to find dependency`() {
        // arrange
        val expectedCarWheels = 5
        val garageCarWheels = 10
        val landVehiclesModule = module {
            factory { (wheels: Int) -> Car(wheels) }
            singleton { Bike() }
        }
        val seaVehiclesModule = module {
            singleton { Boat() }
        }
        val garageModule = module(dependsOn = arrayOf(landVehiclesModule, seaVehiclesModule)) {
            factory {
                val car: Car = get(Parameters.of(garageCarWheels))
                val bike: Bike = get()
                val boat: Boat = get()

                Garage(listOf(car, bike, boat))
            }
        }

        // act
        val component = component {
            withModule(garageModule)
        }

        val car: Car = component.get(Parameters.of(expectedCarWheels))
        val bike: Bike = component.get()
        val boat: Boat = component.get()

        val garage: Garage = component.get()

        // assert
        car.shouldNotBeNull()
        car.wheels shouldBeEqualTo expectedCarWheels
        bike.shouldNotBeNull()
        boat.shouldNotBeNull()
        garage.vehicles.first().wheels shouldBeEqualTo garageCarWheels
    }
}