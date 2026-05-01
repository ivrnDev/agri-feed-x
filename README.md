# 🐔 AGRIFEED X – IoT Smart Poultry Feeding System

## 📌 Overview

**AGRIFEED X** is an Internet of Things (IoT)-based automation system designed to modernize and optimize poultry farm feeding operations for hens and quails.

It enables farmers to remotely control and automate feeding schedules through a mobile application while monitoring feed levels in real time using IoT sensors.

The system integrates **Android (Java)**, **ESP8266 microcontroller**, **Blynk Cloud**, and **Firebase Realtime Database** to deliver a smart, connected farming solution.

---

## ⚙️ Key Features

* 📱 **Android Mobile Application (Java)**

  * Smart feeding mode
  * Scheduled feeding
  * Time-interval feeding

* 🌐 **IoT Device Integration**

  * ESP8266 microcontroller with Wi-Fi capability
  * Real-time communication with cloud services

* ☁️ **Cloud Services**

  * Blynk Cloud for IoT command execution
  * Firebase Realtime Database for user and system data

* 📊 **Food Level Monitoring**

  * Sensor-based feed level detection
  * Real-time data updates

* 🔐 **Authentication System**

  * Secure login system using Firebase database validation
  * Prevents unauthorized access to device controls

---

## 🧱 Tech Stack

* **Android (Java)** – Mobile application
* **ESP8266** – IoT microcontroller
* **Blynk Cloud API** – IoT communication layer
* **Firebase Realtime Database** – Backend data storage
* **Retrofit** – API communication (Android)

---

## 🏗️ System Architecture

1. Android app sends feeding commands (smart / schedule / interval)
2. Commands are sent to **Blynk Cloud API**
3. ESP8266 receives commands via Wi-Fi
4. Sensors collect food level data
5. Data is synced to **Firebase & Blynk Cloud**
6. Mobile app displays real-time system status

---

## 🌐 Blynk API Configuration

```java id="blynk_base_url"
BASE_URL = "https://blynk.cloud/";
```

### API Features

* Get virtual pin values
* Update virtual pin values
* Real-time device control

---

## 🔗 Blynk API Service

The `BlynkApiService` handles communication with Blynk Cloud:

* Retrieve sensor values
* Update device states
* Send commands to IoT hardware

---

## 🔥 Firebase Integration

Firebase Realtime Database is used as the system’s cloud backend for:

* 👤 User authentication (custom login system)
* 📦 Storing user accounts
* 🔄 Real-time data synchronization

---

### 📁 Firebase Database Structure

```json id="firebase_structure"
users: {
  "username": {
    "password": "user_password"
  }
}
```

---

### 🔐 Firebase Configuration (Example Only)

⚠️ These values are **sample placeholders only** and not real secrets.

```json id="firebase_config"
{
  "project_info": {
    "_comment": "Example Firebase configuration (not real credentials)",
    "project_number": "451588166169",
    "firebase_url": "https://agrifeed-x-default-rtdb.asia-southeast1.firebasedatabase.app",
    "project_id": "agrifeed-x",
    "storage_bucket": "agrifeed-x.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:451588166169:android:be6c599f3b4f0eb5bd1088",
        "android_client_info": {
          "package_name": "com.ivrndev.poultry_iot"
        }
      },
      "api_key": [
        {
          "_comment": "Example API key only",
          "current_key": "AIzaSyBqCYkKQLGccUcsz9zi1EBihljz9O0QMP0"
        }
      ]
    }
  ]
}
```

---

## 📱 App Modules

* Login & Authentication
* Feeding Control Dashboard
* Feeding Mode Selection
* Scheduling System
* Real-time Sensor Monitoring

---

## 🔌 Firebase Service (Backend Logic)

The app uses a custom Firebase service for:

* Writing data
* Reading database values
* Checking user existence
* Validating login credentials

---

## 🚀 Future Improvements

* 🤖 AI-based feeding optimization
* 📊 Advanced analytics dashboard
* 🌐 Web-based admin panel
* 📡 Multi-farm management system
* 📴 Offline-first support with sync

---

## ⚠️ Security Notes

* Never expose real Firebase API keys publicly
* Always add `google-services.json` to `.gitignore`
* Consider upgrading to Firebase Authentication for production use

```gitignore id="gitignore"
google-services.json
```

---

## 👨‍💻 Developer Notes

This project demonstrates integration of:

* Mobile application development (Android Java)
* IoT hardware communication (ESP8266)
* Cloud backend services (Firebase + Blynk)
* Real-time automation system design

---

## 📌 Summary

**AGRIFEED X** is a smart poultry automation system that combines IoT devices, cloud services, and mobile technology to automate and improve poultry feeding operations with real-time monitoring and control.
