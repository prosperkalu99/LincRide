# Ride Hailing App (Jetpack Compose + Kotlin)

A mini ride‑hailing Android app built with Kotlin and Jetpack Compose.  
It features Google Maps zone selection, fare estimation (distance + demand + traffic), simulated backend, local ride history storage with Room, MVVM architecture, Hilt DI, and unit/UI testing with MockK, JUnit, Espresso, and Compose testing.

---

## 🛠️ Project Setup Instructions

1. Clone or download the repository.
2. In Android Studio:
    - Open the project directory.
    - Ensure `minSdkVersion ≥ 26`, `compileSdkVersion = 35`.
3. Add your Google Maps API Key:
    - In `AndroidManifest.xml`, add:
      ```xml
      <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_GOOGLE_MAPS_API_KEY"/>
      ```
4. Sync Gradle to download dependencies.

---

## ▶️ How to Run the App

1. Connect an Android device or start an emulator.
2. In Android Studio:
    - Select the `app` module.
    - Click **Run** (▶️) or use **Shift + F10**.
3. The app launches directly on the ride request screen with map and traffic.

---

## 🧪 How to Execute Tests

### Unit Tests (JUnit + MockK + Turbine)
In Android Studio:
- Right-click the `test` directory → **Run Tests**.

Or via command line: `./gradlew test`

---

### UI Tests (Compose UI Test & Espresso)
UI tests located in: `app/src/androidTest/java/com/example/lincridetechnicalassessment/presentation/ride/RequestRideScreenTest.kt`

In Android Studio:
- Right-click `androidTest` → **Run UI Tests**  
  Or run: `./gradlew connectedAndroidTest`

---

## ✅ Features Included

- **Jetpack Compose UI** with Google Maps and Traffic Layer
- **MVVM Architecture** with Hilt DI
- **Room** for local ride history storage
- **Use Cases** for clear logic boundaries
- **Mocked Backend** responses via repository-layer
- **Unit & UI Testing**: ViewModel and Compose UI

---

## 🔧 Customization Options

- Configure peak hours or multipliers in `FareConfig.kt`.
- Add more driver profiles.

---

Happy coding!

---

## 📁 Full Source Code Structure
app/
├── src/
│    ├── main/
│    │    ├── java/com/example/ridehailingapp/
│    │    │    ├── MainActivity.kt
│    │    │    ├── MainApplication.kt
│    │    │    ├── di/
│    │    │    │    ├── DatabaseModule.kt
│    │    │    │    ├── RepositoryModule.kt
│    │    │    │    └── UseCaseModule.kt
│    │    │    ├── data/
│    │    │    │    ├── local/
│    │    │    │    │    ├── AppDatabase.kt
│    │    │    │    │    ├── dao/RideDao.kt
│    │    │    │    │    └── entity/RideEntity.kt
│    │    │    │    └── repository/RideRepositoryImpl.kt
│    │    │    ├── domain/
│    │    │    │    ├── model/
│    │    │    │    │    ├── DriverInfo.kt
│    │    │    │    │    ├── FareEstimateResponse.kt
│    │    │    │    │    └── RequestRideResponse.kt
│    │    │    │    ├── repository/RideRepository.kt
│    │    │    │    └── usecase/
│    │    │    │         ├── EstimateFareUseCase.kt
│    │    │    │         ├── RequestRideUseCase.kt
│    │    │    │         ├── SaveRideUseCase.kt
│    │    │    │         └── GetRideHistoryUseCase.kt
│    │    │    ├── presentation/
│    │    │    │    ├── navigation/
│    │    │    │    │    ├── AppNavHost.kt
│    │    │    │    │    └── NavigationRoute.kt
│    │    │    │    └── ride/requestRide/
│    │    │    │    |    ├── RequestRideScreen.kt
│    │    │    │    |    ├── RequestRideUiState.kt
│    │    │    │    |    └── RequestRideViewModel.kt
│    │    │    │    └── ride/rideHistory/
│    │    │    │         ├── RideHistoryScreen.kt
│    │    │    │         ├── RideHistoryUiState.kt
│    │    │    │         └── RideHistoryViewModel.kt
│    │    │    └── util/
│    │    │         └── FareConfig.kt
│    │    └── res/… (layouts, themes, values)
├── src/test/java/…/RideViewModelTest.kt
└── src/androidTest/java/…/RideScreenTest.kt