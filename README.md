# Popular Movie List — KMP

A sample Kotlin Multiplatform app that showcases modern KMP development practices through a real use
case: browsing popular movies from [TMDb](https://www.themoviedb.org/), viewing details, similar
movies, and user reviews – with pagination, pull-to-refresh, and offline support.

- **Android**: Jetpack Compose UI
- **iOS**: SwiftUI UI
- **Shared**: business logic, networking, database, ViewModels

---

## What this project demonstrates

- **Kotlin Multiplatform** with platform-native UIs sharing all business logic: networking,
  persistence, ViewModels, use cases, and DI wiring live in `:shared`; only the UI layers are
  platform-specific
- **Clean Architecture** with strict layer separation: presentation → domain → data, across a
  multi-module Gradle project with a matching module hierarchy in both `:androidApp` and `:shared`
- **MVI pattern** – each screen owns a single immutable `StateFlow<UiState>`; all changes flow
  through typed intents, making state transitions predictable and fully testable
- **Business use cases** cover a reasonable scope for a sample project – the project is scalable,
  without being over-engineered

---

## Features

- Popular movies list with infinite scroll and pull-to-refresh
- Movie detail screen with similar movies carousel and user reviews
- Per-section loading, error, and retry states
- Offline fallback via SQLDelight for cached data
- Graceful error handling throughout

---

## Tech Stack

| Area | Libraries |
|---|---|
| Language | Kotlin 2.3 · Coroutines · Flow |
| UI (Android) | Jetpack Compose · Material 3 · Coil 3 |
| UI (iOS) | SwiftUI |
| Architecture | Clean Architecture · MVI · Multi-module · KMP |
| DI | Koin 4.1 |
| Networking | Ktor 3.1 (OkHttp on Android · Darwin/NSURLSession on iOS) |
| Persistence | SQLDelight 2.0 |
| Serialization | kotlinx.serialization |
| Date/time | kotlinx-datetime |
| Settings | multiplatform-settings |
| Build | Gradle 9 · AGP 9 · KSP2 · Version Catalog |

---

## Architecture

```
popular-movie-list-kmp
├── androidApp/
│   ├── app/                    # App entry point, Koin setup, navigation routes
│   ├── core/
│   │   ├── navigation/         # Compose ↔ shared navigation bridge
│   │   └── ui/                 # Reusable Compose components & theme
│   └── feature/
│       └── movies/
│           ├── presentation/   # Compose screens, ViewModels, UiState, widgets
│           └── navigation/     # Android screen implementations for Compose nav
├── shared/
│   ├── core/
│   │   ├── navigation/         # Cross-platform navigation controller
│   │   ├── network/            # Ktor HTTP client configuration
│   │   ├── storage/            # SQLDelight driver factory (expect/actual)
│   │   └── usecase/            # Base UseCase interfaces
│   └── feature/
│       └── movies/
│           ├── data/           # DTOs, repository implementations, Ktor REST client
│           ├── domain/         # Domain models, repository interfaces, use cases
│           └── navigation/     # Cross-platform route definitions
└── iosApp/                     # SwiftUI entry point (consumes Shared.framework)
```

Each feature follows a three-layer structure, with the top layer being the only part that differs
between platforms:

```
┌──────────────────────────────────────────────────────────────────┐
│  Platform UI  (Android: Compose · iOS: SwiftUI)                  │
│    ↓ intents              ↑ StateFlow<UiState>                   │
├──────────────────────────────────────────────────────────────────┤
│  Shared: Domain  (ViewModels + UseCases + Repository interfaces) │
│    • Single-responsibility UseCases                              │
│    • No platform dependencies                                    │
├──────────────────────────────────────────────────────────────────┤
│  Shared: Data  (Repository implementations + SQLDelight + Ktor)  │
│    • Network-first; in-memory + SQLDelight fallback              │
└──────────────────────────────────────────────────────────────────┘
```

---

## Data Persistence

The application loads data in this priority:

**Cache → Internet → Database**

If the required data is available in the runtime cache, the app uses it directly – no API call is
made even if a connection is available. This is a good approach for mobile devices where network
conditions are unreliable.

If the data is not cached, the app fetches it from the internet, saves the result to the database
and populates the cache for subsequent reads.

If the internet call fails, the app falls back to data previously saved in the database.

---

## Testing

| Layer | Module | Type | Tools |
|---|---|---|---|
| Screen UI (loading / error / content states) | `androidApp/feature/movies` | Compose instrumented tests | ui-test-junit4 · createComposeRule |
| ViewModel (MVI state transitions) | `androidApp/feature/movies` | JVM unit tests | JUnit 4 · MockK · coroutines-test · Turbine |
| Repository (cache / offline logic) | `shared/feature/movies` | JVM unit tests | JUnit 4 · MockK · coroutines-test · SQLDelight in-memory driver |

**Compose UI tests** call screen content composables directly with a hand-crafted `UiState` – no
ViewModel, no Koin, no network. Each test is a pure UI assertion.

**ViewModel tests** verify `UiState` transitions including intermediate `Loading` states captured
with Turbine – confirming that states are emitted in the right order, not just that the final state
is correct.

**Repository tests** focus on non-obvious behaviour: in-memory cache hits that prevent duplicate
network calls, SQLDelight offline fallback, pagination accumulation across pages, and the guard that
rethrows when offline and the database is also empty. Tests use a real in-memory SQLDelight database
(`JdbcSqliteDriver.IN_MEMORY`) for the persistence layer and MockK for the network API — verifying
actual SQL queries alongside cache and fallback logic.

```bash
./gradlew :androidApp:feature:movies:connectedDebugAndroidTest  # UI tests (device required)
./gradlew :androidApp:feature:movies:testDebugUnitTest          # ViewModel unit tests
./gradlew :shared:feature:movies:testAndroidHostTest            # repository unit tests
```

---

## CI

Every pull request to `main` runs two GitHub Actions checks:

| Workflow | What it does |
|---|---|
| **Unit Tests** | Runs shared repository tests (`androidHostTest`) and Android ViewModel tests (`testDebugUnitTest`) |
| **Lint** | Runs Android lint via `lintDebug`; uploads HTML report as artifact |

---

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
