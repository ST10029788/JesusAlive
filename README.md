
# Reflections of Faith

Repository for the XBCAD Module for Jesus Alive Ministries.
All supporting documentation found [here](https://drive.google.com/drive/folders/1KRhs-EBxGe6Vkipllho2r-JUNwd8YTc-?usp=sharing)

Figma Wireframes can be found [here](https://www.figma.com/design/DcaXUXl4wDbnxy2uZmPJGY/Reflections-of-Faith?node-id=9-2&t=9gjmUDTCxQWXLELS-1)

Watch the demo video [here](https://drive.google.com/drive/folders/13IY6ba-TO8VBaLGxAMltOcKXUvyRxRGF?usp=sharing).


## Introduction

The **Jesus Alive Ministries Church App** is a mobile application designed to enhance the spiritual and community experience of churchgoers. It offers a user-friendly platform where church members can stay connected with Jesus Alive Ministries, access devotional materials, view sermons, track events, donate to causes, and engage with the community. The app respects religious sensitivities and ensures privacy, adhering to POPIA regulations.

## Project Overview

**Reflections of Faith** is an Android application tailored for small churches, particularly in rural areas, to foster communication, engagement, and spiritual growth. It provides features like user account management, access to devotional content, a church events feed, donations, chat rooms, and sermon streaming. The app is designed to function in areas with limited internet access and is scalable to accommodate growing church communities.

## Key Features

- **User Account Management**: Register, login, and manage user profiles. Password recovery and profile updates are supported.
- **Devotional Materials**: Browse, download, and read devotional materials such as books and PDFs.
- **Church Events Feed**: Stay informed on upcoming events, sermons, and other activities.
- **Donation Market**: Browse donation ads, chat with sellers, and manage listings.
- **Real-time Chat Room**: Engage with congregants in live discussions.
- **Notices and Announcements**: View important church notices and announcements.
- **Sermon Streaming**: Watch or listen to sermons directly through the app.
- **Offline Mode**: Download devotional materials and Bibles for offline access.

## Project Objectives

- Build a scalable mobile app to help small churches maintain communication with their congregation.
- Enable users to engage with spiritual materials (devotions, sermons) even with limited internet access.
- Provide a marketplace for donations related to church causes.
- Keep congregants informed about church events and notices.
- Facilitate real-time communication through chat rooms.

## Table of Contents

- [Installation and Setup](#installation-and-setup)
- [Application Structure](#application-structure)
- [Permissions](#permissions)
- [Activities](#activities)
- [Services](#services)
- [Meta Data](#meta-data)
- [Usage Instructions](#usage-instructions)
- [Contribution Guidelines](#contribution-guidelines)
- [License](#license)
- [Video Link](#video-link)

## Installation and Setup

### Prerequisites

Ensure the following are installed:

- **Android Studio**: The official IDE for Android development.
- **Java Development Kit (JDK)**: Version 11 or later.
- **Gradle**: Android's build system (included with Android Studio).

### Steps

1. **Download and Install Android Studio**
   - Visit the [Android Studio download page](https://developer.android.com/studio).
   - Install the necessary SDKs during setup.
   
2. **Clone the Repository**

    ```bash
    git clone [https://github.com/your-username/ReflectionsOfFaith.git](https://github.com/ST10029788/ReflectionsOfFaith.git)
    cd ReflectionsOfFaith
    ```

3. **Open the Project in Android Studio**
   - Select "Open an existing Android Studio project" and choose the project directory.

4. **Configure Gradle**
   - Verify that the `gradle-wrapper.properties` points to the correct version.
   
   ```gradle
   classpath 'com.android.tools.build:gradle:8.0.0'
   ```

5. **Sync Gradle**
   - Go to the **File** menu and select **Sync Project with Gradle Files**.

6. **Set Up Firebase**
   - Create a project in [Firebase Console](https://console.firebase.google.com/).
   - Download the `google-services.json` file and place it in the `app/` directory.
   - Add the Firebase dependencies in the `build.gradle` files:

     ```gradle
     dependencies {
         implementation 'com.google.firebase:firebase-auth:21.0.6'
         implementation 'com.google.firebase:firebase-firestore:24.0.1'
         implementation 'com.google.firebase:firebase-messaging:23.0.0'
     }
     ```

7. **Run the Application**
   - Connect a device or use an emulator and click **Run** or press `Shift + F10` in Android Studio.

## Application Structure

- **Manifest File**: `AndroidManifest.xml`
- **Main Application Class**: `MyApplication`
- **Activities**: Handles user interactions.
- **Services**: Handles notifications and messaging in the background.

## Permissions

The app requests the following permissions:

- **Internet**: `android.permission.INTERNET` for network operations.
- **Storage**: `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE` for accessing files.
- **Camera**: `android.permission.CAMERA` for camera functions.
- **Location**: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION` for location services.
- **Notifications**: `POST_NOTIFICATIONS` for sending notifications.

## Activities

- **Main Activities**: Home screen, login, account management, events, and sermons.
- **Bibles Activities**: View, edit, and manage devotional materials.
- **Chat Room Activities**: Chat functionalities and media uploads.
- **Church Events Activities**: Upload and download event-related media.
- **Donation Market Activities**: Create, browse, and manage donation listings.
- **Notices**: View and manage church notices.
- **Sermons**: Access sermons and lectures.

## Services

- **Firebase Cloud Messaging Service**: Handles notifications via Firebase.

## Meta Data

- **Preloaded Fonts**: Specifies preloaded fonts.
- **Google Maps API Key**: Required for Google Maps functionality.

## Usage Instructions

1. Clone the repository and open it in Android Studio.
2. Ensure dependencies are installed and the Google Maps API key is added.
3. Build and run the application on an emulator or physical device.

## Contribution Guidelines

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch.
3. Submit a pull request with a detailed description of your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Video Link

Watch the demo video [here](https://drive.google.com/drive/folders/1KRhs-EBxGe6Vkipllho2r-JUNwd8YTc-?usp=sharing).

