# 📱 ImageLoader Android App

An Android application to load images from a URL using both `AsyncTask` and `AsyncTaskLoader`, with features like network monitoring and background service notifications.

By Doan Minh Khanh
---

## 🚀 Features

- Load image from a given URL.
- Two async methods: `AsyncTask` and `AsyncTaskLoader`.
- Network connectivity monitoring using `BroadcastReceiver`.
- Background service with periodic notifications.
- Material Design UI using ConstraintLayout.

---

## 📲 Instructions to Run

1. Clone or download the project.
2. Open the project in Android Studio.
3. Ensure your emulator or physical device has internet access.
4. Run the app on an Android 6.0+ device or emulator.

---

## 🛠️ Setup Steps

### ✅ Permissions
In `AndroidManifest.xml`, the following permissions are declared:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
