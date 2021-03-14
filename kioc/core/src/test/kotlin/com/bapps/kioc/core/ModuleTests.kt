package com.bapps.kioc.core

import com.bapps.kioc.core.dsl.module
import com.bapps.kioc.core.dsl.singleton
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class ModuleTests {

    @Test
    fun `Module registration should base on specified qualifier`() {
        // arrange
        val module = Module()
        val firstCar = Car()
        val secondCar = Car()

        // act
        module.register<Vehicle>(Singleton(ModuleScope(module)) { firstCar })
        module.register(Singleton(ModuleScope(module)) { secondCar })
        val vehicleProvider = module.require<Vehicle>()
        val carProvider = module.require<Car>()

        // assert
        vehicleProvider.get() shouldBeEqualTo firstCar
        carProvider.get() shouldBeEqualTo secondCar
    }

    @Test(expected = DuplicatedDependencyException::class)
    fun `Module should throw exception on second registration of the same type`() {
        // arrange
        val module = Module()

        // act
        module.register(Singleton<Vehicle>(ModuleScope(module)) { Car() })
        module.register(Singleton<Vehicle>(ModuleScope(module)) { Car() })
    }

    @Test
    fun `Name qualifiers should allow to register the same type twice`() {
        // arrange
        val module = Module()
        val expectedCar = Car()
        val expectedBike = Bike()
        val qualifierFirst: Qualifier = NameQualifier("first")
        val qualifierSecond: Qualifier = NameQualifier("second")

        // act
        module.register(qualifierFirst, Singleton(ModuleScope(module)) { expectedCar })
        module.register(qualifierSecond, Singleton(ModuleScope(module)) { expectedBike })
        val carProvider = module.require<Vehicle>(qualifierFirst)
        val bikeProvider = module.require<Vehicle>(qualifierSecond)

        // assert
        carProvider.get() shouldBeEqualTo expectedCar
        bikeProvider.get() shouldBeEqualTo expectedBike
    }

    @Test
    fun `There should be relation between not dependent modules`() {
        // arrange
        val carModule = module {
            singleton<Vehicle> { Car() }
        }
        val boatModule = module {
            singleton<Vehicle> { Boat() }
        }

        // act
        val carProvider = carModule.require<Vehicle>()
        val boatProvider = boatModule.require<Vehicle>()

        // assert
        carProvider.get() shouldBeInstanceOf Car::class
        boatProvider.get() shouldBeInstanceOf Boat::class
    }
}