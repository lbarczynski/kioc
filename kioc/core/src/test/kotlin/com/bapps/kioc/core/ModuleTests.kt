package com.bapps.kioc.core

import com.bapps.kioc.core.dsl.module
import com.bapps.kioc.core.dsl.single
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
        module.register<Vehicle>(Single(ModuleScope(module)) { firstCar })
        module.register(Single(ModuleScope(module)) { secondCar })
        val vehicle = module.get<Vehicle>()
        val car = module.get<Car>()

        // assert
        vehicle shouldBeEqualTo firstCar
        car shouldBeEqualTo secondCar
    }

    @Test(expected = DuplicatedDependencyException::class)
    fun `Module should throw exception on second registration of the same type`() {
        // arrange
        val module = Module()

        // act
        module.register(Single<Vehicle>(ModuleScope(module)) { Car() })
        module.register(Single<Vehicle>(ModuleScope(module)) { Car() })
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
        module.register(qualifierFirst, Single(ModuleScope(module)) { expectedCar })
        module.register(qualifierSecond, Single(ModuleScope(module)) { expectedBike })
        val receivedCar: Vehicle = module.get(qualifierFirst)!!
        val receivedBike: Vehicle = module.get(qualifierSecond)!!

        // assert
        receivedCar shouldBeEqualTo expectedCar
        receivedBike shouldBeEqualTo expectedBike
    }

    @Test
    fun `There should be relation between not dependent modules`() {
        // arrange
        val carModule = module {
            single<Vehicle> { Car() }
        }
        val boatModule = module {
            single<Vehicle> { Boat() }
        }

        // act
        val car: Vehicle = carModule.require()
        val boat: Vehicle = boatModule.require()

        // assert
        car shouldBeInstanceOf Car::class
        boat shouldBeInstanceOf Boat::class
    }
}