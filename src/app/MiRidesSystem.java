package app;

import java.util.Scanner;

import components.Car;
import userInterface.Menu;

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

		// TODO: check to see if car with same regNo already in fleet

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
					
					//  Return to menu
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

}
