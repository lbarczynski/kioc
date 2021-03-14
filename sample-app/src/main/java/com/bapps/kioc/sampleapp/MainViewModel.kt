package com.bapps.kioc.sampleapp

import androidx.lifecycle.ViewModel

class MainViewModel(val text: String) : ViewModel() {

    override fun toString(): String {
        return "MainViewModel: ${hashCode()}"
    }
}