package com.bapps.kioc.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bapps.kioc.androidx.dsl.lazyInjection
import com.bapps.kioc.androidx.dsl.lazyViewModel
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val singleton: SimpleClass by lazyInjection()
    private val factory: ComplexClass by lazyInjection(Parameters.of("Hello!"))
    private val viewModel: MainViewModel by lazyViewModel(this, Parameters.of("Hello from VM!"))

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