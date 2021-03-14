package com.bapps.kioc.androidx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.core.*

//typealias ViewModelInstanceFactory<T> = ModuleScope.(ViewModelParameters) -> T
//
//class ViewModelParameters(val viewModelStoreOwner: ViewModelStoreOwner, vararg parameters: Any) :
//    Parameters(mutableListOf<Any>().apply { addAll(parameters) }.toTypedArray())

inline fun <reified T : ViewModel> Module.viewModel(qualifier: Qualifier, noinline instanceFactory: InstanceFactory<T>) {
    register(qualifier, ViewModelDependencyProvider(ModuleScope(this), instanceFactory))
}

inline fun <reified T : ViewModel> Module.viewModel(noinline instanceFactory: InstanceFactory<T>) {
    register(ViewModelDependencyProvider(ModuleScope(this), instanceFactory))
}