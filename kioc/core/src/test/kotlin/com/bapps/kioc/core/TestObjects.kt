package com.bapps.kioc.core

import java.io.PipedReader

interface Vehicle
class Car : Vehicle
class Bike : Vehicle
class Boat : Vehicle

class Garage(val vehicles: List<Vehicle>)