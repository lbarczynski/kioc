![logo](./docs/kioc_logo_transparent.png)

# kIOC
 
Simple lightweight Kotlin IOC container with DSL API. Inspired by [koin](https://github.com/InsertKoinIO/koin) & [Dagger](https://github.com/google/dagger)


# Quick start

1. Define your modules
2. Build component out of them
3. Use component to quick injections

## Providers

### Singleton

Implements singleton pattern. One created instance is returned every time:

```kotlin
singleton { Bike() }
```

### Factory

Implements factory pattern. Presented factory method is used every time to create new instance when injection method is called.

```kotlin
factory { Bike() }
```

### Parametrized provider

```kotlin
factory<Book> { (title: String) ->
    val library: Library = get()
    library.find { it.title == title }
}

val book: Book = component.get(Parameters.of("Salem's Lot"))
```

### Type casting

If you want to register your instance as base type, just define it as provider parameter.

```kotlin
singleton<Vehicle> { Car() }
val vehicle : Vehicle = component.get()
```

### Name your instances

Sometimes you have to use two instances of the same type in single module (it's code smell, but it's happen). In that case you name qualifier to tag specified instance.

```kotlin
singleton<Vehicle>(named("car")) { Car() }
singleton<Vehicle>(named("bike")) { Bike() }


val car : Vehicle = component.get(named("car"))
val bike : Vehicle = component.get(named("bike"))
```

## Modules and component declaration

To build your IOC component you have to group your providers definitions into modules and then build component out of them:

```kotlin
val garageModule = module(dependsOn = arrayOf(/* optional dependent modules */)) {

    single { Bike() }
    factory { Bike() }
    single { Bike() }

    factory {
        val car: Car = get(Parameters.of(garageCarWheels))
        val bike: Bike = get()
        val boat: Boat = get()

        Garage(listOf(car, bike, boat))
    }
}

val component = component {
    withModule(garageModule)
}

val instance : Garage = component.get()
```

## Lazy injection

There is also lazy loading possible. Just use following method:

```kotlin
val vehicle : Vehicle by component.lazyInjection()
```

# AndroidX support

`com.bapps.kioc.androidx` is extension library with Android focused tools

## ViewModel provider

ViewModel provider allows to define factory for ViewModel types. Under the hood it's using Google's `androidx.lifecycle.ViewModelProvider` and `androidx.lifecycle.ViewModelProvider.Factory`, so benefits of using it are exactly the same. ViewModel provider be parameterized as well.

### ViewModel provider declaration

```kotlin
val appModule = module(dependsOn = apiServicesModule) { 
    viewModel {
        val api: BackendApiService = get()
        MainViewModel(api)
    }
}
```

### Inject ViewModel instance into your view

```kotlin

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by component.lazyViewModel(viewModelStoreOwner = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

    }
}
```


<!-- ## Getting Help -->

<!-- Any question about Koin usage? 
- Come talk on slack [#koin](https://kotlinlang.slack.com/?redir=%2Fmessages%2Fkoin) channel
- Post your question on [Stackoverflow - #koin tag](https://stackoverflow.com/questions/tagged/koin) -->

<!-- ### Reporting issues -->

<!-- Found a bug or a problem on a specific feature? Open an issue on [Github issues](https://github.com/InsertKoinIO/koin/issues) -->


<!-- # Contributing 🛠 -->

<!-- Want to help or share a proposal about Koin? problem on a specific feature? 

- Open an issue to explain the issue you want to solve [Open an issue](https://github.com/InsertKoinIO/koin/issues)
- Come talk on slack [#koin-dev](https://kotlinlang.slack.com/?redir=%2Fmessages%2Fkoin-dev) channel
- After discussion to validate your ideas, you can open a PR or even a draft PR if the contribution is a big one [Current PRs](https://github.com/InsertKoinIO/koin/pulls)

Additional readings about basic setup: https://github.com/InsertKoinIO/koin/blob/master/CONTRIBUTING.adoc -->