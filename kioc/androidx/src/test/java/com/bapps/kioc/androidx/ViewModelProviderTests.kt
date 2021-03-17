package com.bapps.kioc.androidx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.androidx.dsl.viewModel
import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.module
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelProviderTests {

    @Test
    fun `ViewModel provider should return new instance for new activity`() {
        // arrange
        val viewModelStoreOwner: ViewModelStoreOwner = mockk {
            every { viewModelStore } answers { ViewModelStore() }
        }
        val component = component {
            module {
                viewModel { TestViewModel() }
            }
        }

        // act
        val testViewModel1: TestViewModel = component.viewModel(viewModelStoreOwner)
        val testViewModel2: TestViewModel = component.viewModel(viewModelStoreOwner)

        // assert
        testViewModel1.shouldNotBeNull()
        testViewModel2.shouldNotBeNull()
        testViewModel1 shouldNotBeEqualTo testViewModel2
    }

    @Test
    fun `ViewModel provider should return the same instance for the same ViewModelStoreOwner`() {
        // arrange
        val viewModelStoreOwner: ViewModelStoreOwner = mockk {
            every { viewModelStore } returns ViewModelStore()
        }
        val component = component {
            module {
                viewModel { TestViewModel() }
            }
        }

        // act
        val testViewModel1: TestViewModel = component.viewModel(viewModelStoreOwner)
        val testViewModel2: TestViewModel = component.viewModel(viewModelStoreOwner)

        // assert
        testViewModel1.shouldNotBeNull()
        testViewModel2.shouldNotBeNull()
        testViewModel1 shouldBeEqualTo testViewModel2
    }
}