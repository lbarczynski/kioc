package com.bapps.kioc.core

interface Vehicle {
    val wheels: Int
}

class Car(override val wheels: Int = 4) : Vehicle
class Bike(override val wheels: Int = 2) : Vehicle
class Boat(override val wheels: Int = 0) : Vehicle

class Garage(val vehicles: List<Vehicle>)