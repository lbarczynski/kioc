package com.bapps.kioc.core

import java.lang.RuntimeException

class DependencyNotFoundException(qualifier: Qualifier) :
    RuntimeException("Dependency for qualifier $qualifier not found")

class DuplicatedDependencyException(qualifier: Qualifier) :
    RuntimeException("There is already one dependency registered for qualifier = $qualifier")