package com.bapps.kioc.core

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class ModuleTests {

    @Test
    fun `Module registration should base on specified qualifier`() {
        // arrange
        val module = Module()
        val firstCar = Car()
        val secondCar = Car()

        // act
        module.register<Vehicle>(Single { firstCar })
        module.register(Single { secondCar })
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
        module.register(Single<Vehicle> { Car() })
        module.register(Single<Vehicle> { Car() })
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
        module.register(qualifierFirst, Single { expectedCar })
        module.register(qualifierSecond, Single { expectedBike })
        val receivedCar: Vehicle = module.get(qualifierFirst)
        val receivedBike: Vehicle = module.get(qualifierSecond)

        // assert
        receivedCar shouldBeEqualTo expectedCar
        receivedBike shouldBeEqualTo expectedBike
    }
}