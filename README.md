# Weather App

## Overview
The Weather App is a mobile application designed to provide users with essential weather information, including:

* Current temperature
* Hourly weather forecast for upcoming hours
* Wind speed, pressure, and visibility
* Users can add multiple cities to their list, with the first city being the primary one. This primary city can be changed as needed.


The application is planned to be continuously developed. Future features include:

* Current location weather data, subject to user consent
* Navigation from the saved cities list to detailed weather information for a specific city

## Technologies Used
* Kotlin: Main programming language used for the app.
* MVVM Pattern: Architectural pattern for separation of concerns.
* Retrofit: For network requests to fetch weather data.
* Glide: For loading and displaying images.
* Coroutines: For asynchronous programming.
* Result: For capturing success or error states.
* API: Data is fetched from the OpenWeatherMap API.

## Features

* Current Temperature: Display the current temperature for the selected city.
* Hourly Forecast: Provide hourly weather information for the next few hours.
* Wind, Pressure, Visibility: Show current wind speed, atmospheric pressure, and visibility.
* Multiple Cities: Add more than one city to the list, with the ability to set any city as the main one.

## Future Enhancements
* Current Location Weather: Allow the app to fetch weather data for the user's current location upon confirmation.
* Detailed City View: Enable navigation from the list of saved cities to a detailed weather fragment for the selected city.

## Setup Instructions
To set up the project locally, follow these steps:

1. Clone the Repository:
2. 
```
git clone https://github.com/yourusername/weather-app.git
cd weather-app
```
2. Open the Project
* Open the project in Android Studio.

3. Configure API Key

* Obtain an API key from OpenWeatherMap.
* Add your API key to the project by placing it in the secret.properties (copy `secret.properties.template`)

```OPENWEATHERMAP_API_KEY="YOUR_API_KEY_HERE"```

4. Build and Run:

* Sync the project with Gradle files.
* Build and run the application on an emulator or physical device.
## Contribution
Contributions are welcome! Feel free to open issues or submit pull requests for new features, bug fixes, or improvements.

## License
This project is licensed under the MIT License.
