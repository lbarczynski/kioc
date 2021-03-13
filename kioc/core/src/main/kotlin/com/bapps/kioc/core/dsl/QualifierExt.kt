package com.bapps.kioc.core.dsl

import com.bapps.kioc.core.NameQualifier

fun named(name: String) = NameQualifier(name)