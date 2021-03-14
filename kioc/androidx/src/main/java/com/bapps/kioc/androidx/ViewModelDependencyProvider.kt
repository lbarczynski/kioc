package com.bapps.kioc.androidx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.core.InstanceFactory
import com.bapps.kioc.core.ModuleScope
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.core.Provider

class ViewModelDependencyProvider<T : ViewModel>(
    private val moduleScope: ModuleScope,
    private val instanceFactory: InstanceFactory<T>
) : Provider<T> {
    @ExperimentalStdlibApi
    override fun get(parameters: Parameters): T {
        val viewModelStoreOwner = parameters.parameters.first() as ViewModelStoreOwner
        val reducedParameters = Parameters(parameters.parameters.removeFirst())
        val factory = ViewModelFactory(moduleScope, instanceFactory, reducedParameters)
        return ViewModelProvider(viewModelStoreOwner, factory)
            .get(ViewModelHolder::class.java)
            .nestedViewModel as T
    }
}

private class ViewModelFactory<T : ViewModel>(
    private val moduleScope: ModuleScope,
    private val instanceFactory: InstanceFactory<T>,
    private val parameters: Parameters
) : ViewModelProvider.Factory {

    override fun <R : ViewModel> create(modelClass: Class<R>): R {
        return ViewModelHolder(instanceFactory(moduleScope, parameters)) as R
    }
}

class ViewModelHolder(val nestedViewModel: ViewModel) : ViewModel()

private fun List<*>.removeFirst() = toMutableList().apply { removeAt(0) }.toList()