# recover
yet another issue tracker

This project is an attempt to learn the most recent Android technology while still producing something useful, scalable. It will also be used as an example in new coding blog on topic 'Android App Architecture'.

this project included following
* [Android App Architecture](https://developer.android.com/jetpack/guide) as app architecture and file structure
* [Hlit](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
* [Room](https://developer.android.com/training/data-storage/room) for local database
* [Retrofit](https://square.github.io/retrofit/) for REST api calls
* [Coroutines](https://developer.android.com/kotlin/coroutines) in [App Architecture](https://developer.android.com/topic/libraries/architecture/coroutines) for asynchronous task managing
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for separate out view data ownership from UI controller logic.
* [Livedata](https://developer.android.com/topic/libraries/architecture/livedata) observable data from ViewModel
* Unit test using junit and mockito

backend sourcecode that use in this project will be available at [Recover-api](https://github.com/echoneet/recover-api)

## TODO
- [ ] implement other key features (auth, issue detail, comment, live chat, etc.)
- [ ] implement other tests (End-to-end tests, integration tests)
- [ ] try Flow
- [ ] try jetpack compose
