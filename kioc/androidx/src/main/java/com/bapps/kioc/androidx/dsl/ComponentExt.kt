package com.bapps.kioc.androidx.dsl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.androidx.providers.ViewModelParameters
import com.bapps.kioc.core.Component

inline fun <reified T : ViewModel> Component.viewModel(viewModelStoreOwner: ViewModelStoreOwner, vararg parameters: Any) =
    get<T>(ViewModelParameters(viewModelStoreOwner, parameters.toList().toTypedArray()))

inline fun <reified T : ViewModel> Component.lazyViewModel(viewModelStoreOwner: ViewModelStoreOwner, vararg parameters: Any) =
    lazy { get<T>(ViewModelParameters(viewModelStoreOwner, parameters.toList().toTypedArray())) }