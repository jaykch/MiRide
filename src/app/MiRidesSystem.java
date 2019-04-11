/*
* Class: 		MiRidesSystem
* Description: 	The class represents the system that manages 
* 			   	car and booking validation and creation.
* Author: 		Jay Kumar - S3770282
*/

package app;

import java.util.Scanner;

import components.Car;
import userInterface.Menu;
import utils.DateTime;

public class MiRidesSystem {

	// Add a fleet array to store all cars for booking
	private Car[] fleet = new Car[100];

	public MiRidesSystem() {

	}

	// Initialise the Menu, Scanner and MiRidesSystem class to pass them into
	// methods

	Scanner scanner = new Scanner(System.in);
	Menu menu = new Menu();

	// Method to get the fleet array

	public Car[] getFleet() {
		return this.fleet;
	}

	// Method to get all cars in the fleet

	public void getFleetDetails() {

		// Check if the fleet has no cars

		if (fleet[0] == null) {
			System.out.println("There are no cars in the fleet!\n");
		}

		// Loop through the fleet array to fleet a list of all cars

		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {
				System.out.println(fleet[i].getDetails());
			}
		}
		System.out.print("\n");
	}

	// Method to add car to a fleet

	public void addCarToFleet(Car car) {
		
		/*
		* ALGORITHM
		* BEGIN
		* 	LOOP through the array of fleet
		* 		IF array index is empty
		* 			ADD car at that index
		* 	EXIT out of the loop
		* END
		*/

		// loop through fleet array to add to next empty space

		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] == null) {
				fleet[i] = car;
				break;
			}
		}
	}

	// Create a car and add it to the fleet

	public void createCar(String regNo, String make, String model, String driverName, int passengerCapacity) {

		// Check if a car with same registration number exists
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {
				if (fleet[i].getRegNo().equals(regNo)) {
					System.out.println("\nError: A car with the same registration number exists!\n");

					// Return to menu
					menu.goBackToMenu(scanner, this);
				}
			}
		}

		// Create a new car object if above validation is successful
		Car car = new Car(regNo, make, model, driverName, passengerCapacity);
		System.out.println("\nNew Car added successfully for registion number: " + regNo + "\n");

		// Add car to fleet
		this.addCarToFleet(car);

		// Return to menu
		menu.goBackToMenu(scanner, this);

	}

	// Create booking and add it to current bookings for the car

	public void bookCar(String firstName, String lastName, DateTime pickupDate, int numPassengers, Car car) {

		// Check if number of passengers exceed passenger capacity of the car

		if (numPassengers > car.getPassengerCapacity()) {

			System.out.println("\nError: The number of passengers exceed the passanger capacity of this car!");
			System.out.println("Error: Please choose another car...");

			menu.bookCar(scanner, this);

		} else if (car.book(firstName, lastName, pickupDate, numPassengers)) {

			System.out.println("\nThank you for your booking. " + car.getDriverName() + " will pick you up on "
					+ pickupDate.getFormattedDate() + ".\r\n" + "Your booking reference is: " + car.book.getBookingID()
					+ ".\n");
		}

		// Return to menu
		menu.goBackToMenu(scanner, this);
	}

	// Method to get available cars

	public Boolean isAvailableCarsOnDate(DateTime pickupDate) {

		int numAvailableCars = 0;

		// Set cars to available on date for the given pickup date
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {

				// As cars are found in the fleet array add them to the count of available cars
				numAvailableCars++;

				for (int j = 0; j < fleet[i].getCurrentBookings().length; j++) {
					if (fleet[i].getCurrentBookings()[j] != null) {
						String bookedDate = fleet[i].getCurrentBookings()[j].getBookingDate();
						if (bookedDate.equals(pickupDate.getFormattedDate())) {
							fleet[i].setAvailableOnDate(false);

							// As cars are found to be not available take that car out of the count for
							// number of available cars
							numAvailableCars--;
						}else {
							fleet[i].setAvailableOnDate(true);
						}
					}
				}
			}
		}

		// Check if there are available cars on this date
		if (numAvailableCars > 0) {
			return true;
		} else {
			return false;
		}

	}
}
