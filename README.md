MazaadyTask

This project is an Android application for displaying categories and subcategories using Mazaady’s API. It demonstrates clean architecture principles with the separation of concerns into the data, domain, and app modules. The app integrates API calls, handles resources effectively, and uses modern Android development tools like Kotlin coroutines and Hilt for dependency injection.

Features

	•	Fetches categories and their subcategories from Mazaady’s API.
	•	Clean Architecture (data, domain, app modules).
	•	Kotlin Coroutines for asynchronous tasks.
	•	Hilt for Dependency Injection.
	•	MVVM Architecture pattern.
	•	Error handling using a centralized ExceptionHandler.
	•	Resource states (Loading, Success, Error) for managing API responses.

Project Structure

	•	app: Contains the UI layer and application setup.
	•	data: Manages API service, repository, and data models.
	•	domain: Contains the business logic, use cases, and interfaces.

Prerequisites

Before you can build and run this project, ensure that you have the following:

	1.	Android Studio: Make sure you’re running the latest stable version of Android Studio.
	2.	Gradle: Installed via Android Studio.
	3.	API Keys: You’ll need an API key to run this project successfully. Set up the environment variables as described below.

How to Run

1. Clone the Repository

git clone https://github.com/mahmoud947/MazaadyTask.git
cd MazaadyTask

2. Set Up API Keys and Base URL

To run the project, you need to configure the API keys and the base URL. This is done by adding two properties to your gradle.properties file.

	1.	Open the gradle.properties file and add the following:

API_PRIVATE_KEY=your_api_private_key_here
BASE_URL=https://baseurl

Replace your_api_private_key_here with your actual API private key.

3. Build Configuration

The project utilizes buildConfigField in the build.gradle file to inject these variables into the app:

buildConfigField("String", "API_PRIVATE_KEY", "\"${project.findProperty("API_PRIVATE_KEY")}\"")
buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")

Ensure that your environment variables are properly configured as described in the previous step.

4. Sync Gradle

Once you’ve added the API key and base URL to the gradle.properties file, sync the project with Gradle in Android Studio by clicking on “Sync Now” in the prompt that appears, or manually sync by going to:

File -> Sync Project with Gradle Files

5. Run the Project

You can now run the project on an emulator or a physical Android device:

	1.	Open Android Studio.
	2.	Click on Run or use the shortcut Shift + F10.
	3.	Choose the device you want to run the project on.

Testing

The project includes unit tests for the use cases. To run the tests:

	1.	Right-click on the test directory in the project structure.
	2.	Choose Run Tests.

Alternatively, you can use the command line:

./gradlew test

Dependencies

	•	Kotlin Coroutines: For asynchronous programming.
	•	Hilt: For Dependency Injection.
	•	Retrofit: For making network requests.
	•	MockK: For mocking in unit tests.
	•	JUnit: For running unit tests.

