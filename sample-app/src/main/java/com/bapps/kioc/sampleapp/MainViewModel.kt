package com.bapps.kioc.sampleapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    override fun toString(): String {
        return "MainViewModel: ${hashCode()}"
    }
}