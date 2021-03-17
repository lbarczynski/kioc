package com.bapps.kioc.androidx

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.androidx.dsl.lazyViewModel
import com.bapps.kioc.androidx.dsl.viewModel
import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.module
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelExtensionsTests {

    @Test
    fun `ViewModel lazy injection should return instance when reference will be called`() {
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
        val testViewModel1: TestViewModel by component.lazyViewModel(viewModelStoreOwner)
        val testViewModel2: TestViewModel by component.lazyViewModel(viewModelStoreOwner)

        // assert
        verify(exactly = 0) { viewModelStoreOwner.viewModelStore }
        testViewModel1.shouldNotBeNull()
        testViewModel2.shouldNotBeNull()
        verify(exactly = 2) { viewModelStoreOwner.viewModelStore }
    }
}