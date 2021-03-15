package com.bapps.kioc.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bapps.kioc.androidx.dsl.lazyViewModel
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val singleton: SimpleClass by DI.container.lazyInjection()
    private val factory: ComplexClass by DI.container.lazyInjection(Parameters.of("Hello!"))
    private val viewModel: MainViewModel by DI.container.lazyViewModel(this, "Hello from VM!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.singleton = singleton
            it.factory = factory
            it.viewModel = viewModel
        }

    }
}