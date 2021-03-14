package com.bapps.kioc.androidx

import androidx.lifecycle.ViewModel
import com.bapps.kioc.core.*

inline fun <reified T : ViewModel> Module.viewModel(qualifier: Qualifier, noinline instanceFactory: ViewModelInstanceFactory<T>) {
    register(qualifier, ViewModelDependencyProvider(ModuleScope(this), instanceFactory))
}

inline fun <reified T : ViewModel> Module.viewModel(noinline instanceFactory: ViewModelInstanceFactory<T>) {
    register(ViewModelDependencyProvider(ModuleScope(this), instanceFactory))
}