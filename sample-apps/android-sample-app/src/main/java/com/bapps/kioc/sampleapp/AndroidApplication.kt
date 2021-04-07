package com.bapps.kioc.sampleapp

import android.app.Application
import com.bapps.kioc.core.Component
import com.bapps.kioc.core.ComponentProvider

class AndroidApplication : Application(), ComponentProvider {

    override val component: Component
        get() = DI.container

}