# 🛒 Smart Shopping App

The **Smart Shopping App** is a sample Android application designed to deliver a smooth, modern, and intelligent shopping experience. It leverages barcode scanning, local storage, and elegant UI design to simplify how users scan, manage, and checkout their purchases — all in one place.

---

## ✨ Features

### 📦 1. Seamless Product Scanning
Users can easily scan products using barcodes or QR codes. The scanner supports a wide range of barcode formats, ensuring compatibility with most products in a retail environment.

### 🛒 2. Instant Cart Addition
Upon a successful scan, product details are fetched and added directly to the cart. This allows shoppers to keep track of items as they shop — no more forgetting what you picked!

### 💾 3. Persistent Local Storage
All scanned items are saved locally using **Room Database**, which ensures the data remains available even if the app is minimized or the device is rotated. This adds a layer of reliability and improves the overall user experience by preventing accidental data loss.

### 💳 4. Streamlined Checkout with Tax Calculation
Once all items are scanned and added to the cart, users can proceed to checkout. The app calculates the total price of all items and applies tax (e.g., 18%) transparently, helping users understand their final bill.

### ✅ 5. Clean Post-Order Flow
After a successful checkout, the order is confirmed, and the local database is cleared. This ensures users always start fresh with their next shopping trip while maintaining a clutter-free experience.

---

## 🧠 Technical Highlights

- **MVVM Architecture**: Ensures a clean separation of concerns between UI and business logic.
- **Room Database**: Used for local storage of scanned product data, providing persistence and efficient querying.
- **ZXing Barcode Scanner**: An embedded scanner for both 1D and 2D barcode formats, ensuring high compatibility and accuracy.
- **ViewModel + LiveData**: Lifecycle-aware components to keep UI in sync with backend data.
- **Kotlin Coroutines + Flows**: For asynchronous and reactive data handling.
- **Hilt (Dependency Injection)**: Keeps the codebase scalable and easy to test.

---

## 🔒 Why Local DB?
Using Room for local data persistence ensures that:
- Switching between apps or backgrounding doesn't wipe the cart.
- Users have a consistent experience even if something unexpected happens (like screen rotation or phone lock).
- Scanned items are reliably retained until checkout, offering a stress-free shopping journey.

---

## 💡 Summary

The **Smart Shopping App** is a thoughtfully crafted sample app that reimagines how modern shoppers interact with retail technology. By blending convenience, reliability, and a user-friendly interface, it makes everyday shopping more efficient and enjoyable.

---

## 🚀 Getting Started

1. Clone this repository.
2. Add the ZXing dependency:
   ```groovy
   implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
