package com.bapps.kioc.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.androidx.ViewModelParameters
import com.bapps.kioc.androidx.viewModel
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.core.parameters
import com.bapps.kioc.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val singleton: SimpleClass = DI.container.get()
    private val factory: ComplexClass = DI.container.get(parameters("Hello!"))
    private val viewModel: MainViewModel by lazy {
        DI.container.viewModel(this, "Hello from VM!")
    }

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