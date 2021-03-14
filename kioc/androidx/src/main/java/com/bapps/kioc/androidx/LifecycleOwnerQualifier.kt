package com.bapps.kioc.androidx

import androidx.lifecycle.LifecycleOwner
import com.bapps.kioc.core.Qualifier

class LifecycleOwnerQualifier(owner: LifecycleOwner) : Qualifier {
    override val value: String
        get() = TODO("Not yet implemented")
}