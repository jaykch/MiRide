MIRIDE-S3770282 &middot; [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/jaykch/MiRide-S3770282/blob/master/LICENSE)
=======
  <p align="center">
    An awesome project written in Java to implement a system to hire a car from a fleet of cars of a taxi company.
    <br/>
    <a href="https://github.com/jaykch/MiRide-S3770282/issues">Report Bug</a>
  </p>
</p>

## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Author](#author)
* [License](#license)
* [Acknowledgements](#acknowledgments)

## About The Project
This facilitates the creation of cars for booking through a centralised system for a taxi company to manage their bookings and cars.
It has the following functions

* **Create Car:**  Ability to add new cars and drivers acquired by the company. 
* **Book Car:**  Create a booking for one of the cars.
* **Complete Booking:**  Completes a booking.
* **Display All Cars:**  Displays all cars created based on if they are silver service type cars or standard cars and sorts them according to ascending and descending order.
* **Search Specific Car:**  Searches for a specific cars using it's registration ID.
* **Search Available Cars:**  Searches for cars that are not fully booked. 
* **Seed Data:**  Seed Data to test out the system.
* **Exit Program:**  Exits the program and saves previous data in a text file.

### Built With
This project uses the following software and languages
* [JAVA](https://www.java.com/en/download/)
* [Eclipse](https://www.eclipse.org/downloads/)

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

## 🧐 What's inside?

A quick look at the top-level files and directories you'll see in this project.

    .
    ├── JRE System Library
    ├── src
    ├─── app
    ├──── Menu.java
    ├──── MiRideApplication.java
    ├─── cars
    ├──── Booking.java
    ├──── Car.java
    ├──── SilverServiceCar.java
    ├─── exception_handling
    ├──── InvalidBooking.java
    ├──── InvalidCarServiceType.java
    ├──── InvalidDate.java
    ├──── InvalidRefreshments.java
    ├──── InvalidRegID.java
    ├──── InvalidSortOrder.java
    ├──── SilverServiceCarMinimumBookingFee.java
    ├─── main
    ├──── Driver.java
    ├─── utilities
    ├──── DateTime.java
    ├──── DateUtilities.java
    ├──── MiRidesUtilities.java
    ├─── backup_data.txt
    ├─── data.txt
    ├─── License
    ├─── README.md
    ├─── .gitignore
    ├─── .gitignore
    ├── .prettierrc

### Prerequisites
Here is the list of all the prerequisites you would need to install on your system in order to run this device
* Java SE Development Kit 8
```sh
https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
```

### Installation
1. Install Java development kit.
    ```sh
    Install the file downloaded
    ```

## Author
* **Jay Kumar** - *Complete Development* - [jaykch.com](http://www.jaykch.com/)

## License
This project is licensed under the MIT License - see the [LICENSE.md](/LICENSE) file for details

## Acknowledgments

* **Rodney Cocker** - For providing the base code for this application.
