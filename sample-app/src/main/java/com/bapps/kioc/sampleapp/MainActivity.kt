package com.bapps.kioc.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val singleton: SimpleClass = DI.container.get()
    private val factory: ComplexClass = DI.container.get(Parameters("Hello!"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.singleton = singleton
            it.factory = factory
        }

    }
}