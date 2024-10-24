# JesusAlive
Repository for the XBCAD Module for Jesus Alive Ministries 

# Project Name

## Table of Contents
- [Application Structure](#application-structure)
- [Permissions](#permissions)
- [Activities](#activities)
- [Services](#services)
- [Meta Data](#meta-data)
- [Usage Instructions](#usage-instructions)
- [Contribution Guidelines](#contribution-guidelines)
- [License](#license)

## Application Structure
The application is structured as follows:

- **Manifest File**: `AndroidManifest.xml`
- **Main Application Class**: `MyApplication`
- **Activities**: Various activities for handling user interactions and functionalities.
- **Services**: Background services for handling notifications and messaging.

## Permissions
The application requests several permissions to function correctly. Here’s a breakdown of each permission:

- **Internet Access**:
  - `android.permission.INTERNET`: Required for network operations.
  
- **Storage Access**:
  - `android.permission.READ_EXTERNAL_STORAGE`: Allows reading from external storage.
  - `android.permission.WRITE_EXTERNAL_STORAGE`: Allows writing to external storage.
  - `android.permission.READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`, `READ_MEDIA_AUDIO`: Required for accessing media files.
  
- **Camera Access**:
  - `android.permission.CAMERA`: Necessary for camera functionalities.
  
- **Location Access**:
  - `android.permission.ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`: Required for location-based services.
  
- **Notifications**:
  - `android.permission.POST_NOTIFICATIONS`: Allows posting notifications to users.

## Activities
The application consists of multiple activities, each serving a specific purpose. Below is a categorized list of activities defined in the manifest:

### Main Activities
- **Start Page**
- **Main_Home_Screen**: Main entry point of the application.
- **Log_In_Screen**: User login interface allowing different sign-in types:
  - Google
  - Phone Number
  - Email
  - Biometrics
  - Guest
- **About Page**
- **About_Us_Activity**: Provides information about the application and its purpose.

### Account Management
- **Account_Activity**: Handles user account settings and information.
- **Change_Password_Activity**: Allows users to change their passwords.
- **Delete_Account_Activity**: Facilitates account deletion.
- **Forgot_Password_Activity**: Assists users in recovering their passwords.
- **Edit_Profile_Activity**: Enables users to edit their profile information.

### Bibles Activities
- **BooksDashboardUser Activity**: Displays available devotional materials.
- **PdfViewActivity**: Allows users to view PDF documents.
- **PdfDetailActivity**: Provides detailed information about specific PDFs.
- **PdfEditActivity**: Enables editing of PDF documents.
- **BookPdfAdminActivity**: Admin functionality for managing PDFs.
- **PdfAddActivityBooks**: Allows adding new PDF documents.
- **AddCategoryBooksActivity**: Facilitates the addition of new categories for books.
- **BooksAdminDashboardActivity**: Admin dashboard for managing books.

### Chat Room Activities
- **Chat_Room_Activity**: Interface for chat functionalities.
- **Image_Upload_Preview_Activity**: Previews images before uploading.

### Church Events Activities
- **Download_Picture_Activity**: Allows users to download pictures from events.
- **Download_Video_Activity**: Facilitates downloading videos related to events.
- **Add_Video_Activity**: Enables users to add videos for church events.
- **Add_Picture_Activity**: Allows users to upload pictures for events.
- **Socials_Gallery_Activity**: Displays a gallery of social media posts.

### Devotional Material Activities
- Activities related to devotional materials management and viewing.

### Donation Market Activities
- **Chat_Activity**: Chat functionality for the donation market.
- **Ad_Seller_Profile_Activity**: Displays seller profiles for ads.
- **Create_Ad_Activity**: Allows users to create new advertisements.
- **Ad_Details_Activity**: Provides detailed information about specific ads.
- **Location_Picker_Activity**: Enables users to select locations for ads.

### Notices Activities
- **Notices_Activity**: Displays notices to users.
- **Notice_Details_Activity**: Provides detailed information about specific notices.
- **Admin_Notices_Activity**: Admin functionality for managing notices.
- **Admin_Notice_Details_Activity**: Detailed view for admin notices.

### Sermons Page
- **Lectures_Activity**: Displays sermons and lectures for users.

### Utilities
- Activities related to utility functions, such as settings and preferences.

## Services
The application includes a Firebase Cloud Messaging service for handling notifications:

- **Firebase Cloud Messaging Service**: `Firebase_Cloud_Messaging_Service`: Handles incoming messages from Firebase.

## Meta Data
The application utilizes the following meta-data configurations:

- **Preloaded Fonts**: `preloaded_fonts`: Resource for preloaded fonts.
- **Google Maps API Key**: `com.google.android.geo.API_KEY`: Required for Google Maps functionalities.

## Usage Instructions
To run the application:

1. Clone the repository.
2. Open the project in Android Studio.
3. Ensure that all dependencies are installed.
4. Set up the Google Maps API key in `strings.xml`.
5. Build and run the application on an emulator or physical device.

## Contribution Guidelines
Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a description of your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
