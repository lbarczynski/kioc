package com.bapps.kioc.androidx

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelStore
import com.bapps.kioc.androidx.dsl.lazyViewModel
import com.bapps.kioc.androidx.dsl.viewModel
import com.bapps.kioc.core.Component
import com.bapps.kioc.core.ComponentProvider
import com.bapps.kioc.core.dsl.component
import com.bapps.kioc.core.dsl.module
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelExtensionsTests {

    @Test
    fun `ViewModel lazy injection should return instance when reference will be called`() {
        // arrange
        val component = component {
            module {
                viewModel { TestViewModel() }
            }
        }
        val activityMock: ComponentActivity = mockk {
            every { viewModelStore } returns ViewModelStore()
            every { applicationContext } returns ApplicationMock(component)
        }


        // act
        val testViewModel1: TestViewModel by activityMock.lazyViewModel()
        val testViewModel2: TestViewModel by activityMock.lazyViewModel()

        // assert
        verify(exactly = 0) { activityMock.viewModelStore }
        testViewModel1.shouldNotBeNull()
        testViewModel2.shouldNotBeNull()
        verify(exactly = 2) { activityMock.viewModelStore }
    }
}

private class ApplicationMock(_component: Component) : Application(), ComponentProvider {
    override val component: Component = _component
}