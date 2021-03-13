package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.Bike
import com.bapps.kioc.core.Car
import com.bapps.kioc.core.Vehicle
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
}