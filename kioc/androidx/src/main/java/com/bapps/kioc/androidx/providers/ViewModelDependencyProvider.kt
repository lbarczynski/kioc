package com.bapps.kioc.androidx.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.core.ModuleScope
import com.bapps.kioc.core.Parameters
import com.bapps.kioc.core.Provider

typealias ViewModelInstanceFactory<T> = ModuleScope.(ViewModelParameters) -> T

class ViewModelParameters(val viewModelStoreOwner: ViewModelStoreOwner, parameters: Array<Any>) : Parameters(parameters)

class ViewModelDependencyProvider<T : ViewModel>(
    private val moduleScope: ModuleScope,
    private val instanceFactory: ViewModelInstanceFactory<T>
) : Provider<T> {

    override fun get(parameters: Parameters): T {
        val viewModelParameters = parameters as? ViewModelParameters ?: throw ViewModelParametersExpectedException()
        val factory = ViewModelFactory(moduleScope, instanceFactory, viewModelParameters)
        return ViewModelProvider(viewModelParameters.viewModelStoreOwner, factory)
            .get(ViewModelHolder::class.java)
            .nestedViewModel as T
    }
}

private class ViewModelFactory<T : ViewModel>(
    private val moduleScope: ModuleScope,
    private val instanceFactory: ViewModelInstanceFactory<T>,
    private val parameters: ViewModelParameters
) : ViewModelProvider.Factory {

    override fun <R : ViewModel> create(modelClass: Class<R>): R {
        return ViewModelHolder(instanceFactory(moduleScope, parameters)) as R
    }
}

private class ViewModelHolder(val nestedViewModel: ViewModel) : ViewModel()

class ViewModelParametersExpectedException : RuntimeException("Passed parameters are not ViewModelParameters type")