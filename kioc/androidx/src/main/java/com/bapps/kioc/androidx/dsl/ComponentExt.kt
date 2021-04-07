package com.bapps.kioc.androidx.dsl

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.bapps.kioc.androidx.providers.ViewModelParameters
import com.bapps.kioc.core.*

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    parameters: Parameters = Parameters.EMPTY
): Lazy<T> =
    lazy { viewModel(viewModelStoreOwner, parameters) }

inline fun <reified T : ViewModel> Activity.lazyViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    parameters: Parameters = Parameters.EMPTY
): Lazy<T> =
    lazy { viewModel(viewModelStoreOwner, parameters) }

inline fun <reified T : ViewModel> Fragment.viewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    parameters: Parameters = Parameters.EMPTY
): T {
    return when (this) {
        is ComponentProvider -> component.viewModel(viewModelStoreOwner, parameters)
        else -> requireActivity().viewModel(viewModelStoreOwner, parameters)
    }
}

inline fun <reified T : ViewModel> Activity.viewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    parameters: Parameters = Parameters.EMPTY
): T {
    val provider: ComponentProvider = when {
        this is ComponentProvider -> this
        applicationContext is ComponentProvider -> applicationContext as ComponentProvider
        else -> throw ComponentProviderNotDefinedException()
    }
    return provider.component.viewModel(viewModelStoreOwner, parameters)
}

inline fun <reified T : ViewModel> Component.viewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    parameters: Parameters = Parameters.EMPTY
): T {
    return get(ViewModelParameters(viewModelStoreOwner, parameters.parameters.toList().toTypedArray()))
}

inline fun <reified T> Fragment.get(parameters: Parameters = Parameters.EMPTY): T = get(TypeQualifier(T::class), parameters)
inline fun <reified T> Activity.get(parameters: Parameters = Parameters.EMPTY): T = get(TypeQualifier(T::class), parameters)

fun <T> Fragment.get(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): T {
    return when (this) {
        is ComponentProvider -> component.get(qualifier, parameters)
        else -> requireActivity().get(qualifier, parameters)
    }
}

fun <T> Activity.get(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): T {
    val provider: ComponentProvider = when {
        this is ComponentProvider -> this
        applicationContext is ComponentProvider -> applicationContext as ComponentProvider
        else -> throw ComponentProviderNotDefinedException()
    }
    return provider.component.get(qualifier, parameters)
}

inline fun <reified T> Fragment.lazyInjection(parameters: Parameters = Parameters.EMPTY): Lazy<T> =
    lazyInjection(TypeQualifier(T::class), parameters)

inline fun <reified T> Activity.lazyInjection(parameters: Parameters = Parameters.EMPTY): Lazy<T> =
    lazyInjection(TypeQualifier(T::class), parameters)

fun <T> Fragment.lazyInjection(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): Lazy<T> = lazy {
    when (this) {
        is ComponentProvider -> component.get(qualifier, parameters)
        else -> requireActivity().get(qualifier, parameters)
    }
}

fun <T> Activity.lazyInjection(qualifier: Qualifier, parameters: Parameters = Parameters.EMPTY): Lazy<T> = lazy {
    val provider: ComponentProvider = when {
        this is ComponentProvider -> this
        applicationContext is ComponentProvider -> applicationContext as ComponentProvider
        else -> throw ComponentProviderNotDefinedException()
    }
    provider.component.get(qualifier, parameters)
}

class ComponentProviderNotDefinedException :
    Exception("ComponentProvider not defined! You can implement ComponentProvider in your Fragment, Activity or Application class")