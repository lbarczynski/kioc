package com.bapps.kioc.core

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be equal to`
import org.junit.Test

class ProvidersTests {

    @Test
    fun `Single provider should always return the same value`() {
        // arrange
        val provider = Single { SampleStruct(3, "abcd") }

        // act
        val instanceA = provider.get()
        val instanceB = provider.get()

        // assert
        instanceA `should be equal to` instanceB
    }

    @Test
    fun `Factory provider should always return new value`() {
        // arrange
        val provider = Factory { SampleStruct(3, "abcd") }

        // act
        val instanceA = provider.get()
        val instanceB = provider.get()

        // assert
        instanceA `should not be equal to` instanceB
    }
}

private class SampleStruct(val a: Int, val b: String)