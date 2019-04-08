package userInterface;

import java.util.Scanner;
import utils.DateTime;
import app.MiRidesSystem;
import components.Car;
import components.Booking;

public class Menu {

	public Menu() {

	}

	public void run() {

		Scanner scanner = new Scanner(System.in);
		MiRidesSystem system = new MiRidesSystem();

		// -------Seed data---------------->

		Car beetle = new Car("STD999", "Volkswagen", "Beetle", "Justin Beiber", 2);
		Car mustang = new Car("STD199", "Ford", "Mustang", "Justin Beiber", 2);
		Car ferrari = new Car("STD199", "ferarri", "458", "Justin Beiber", 2);

		system.addCarToFleet(beetle);
		system.addCarToFleet(mustang);
		system.addCarToFleet(ferrari);

		// system.getFleet();

		// ---------------------------------------------//
		this.menuOptionHandler(scanner, system);
	}

	// Method to display all options of the menu

	public void displayOptions() {
		// Display menu to view all the application options
		// Use printF to format the menu
		System.out.println("\n*** MiRides System Menu ***\n");
		System.out.printf("%s			%S\n", "Create Car", "CC");
		System.out.printf("%s			%S\n", "Book a Car", "BC");
		System.out.printf("%s		%S\n", "Complete Booking", "CB");
		System.out.printf("%s		%S\n", "Display All Cars", "DA");
		System.out.printf("%s		%S\n", "Search Specific Car", "SS");
		System.out.printf("%s		%S\n", "Search Available Cars", "SA");
		System.out.printf("%s			%S\n", "Seed Data", "SD");
		System.out.printf("%s			%S\n", "Exit Program", "EX");
	}

	// Method to go back to menu from any place in the application

	public void goBackToMenu(Scanner scanner, MiRidesSystem system) {
		while (true) {
			System.out.print("Would you like to go back to the Menu? Y/N ");
			String input = scanner.nextLine();
			if (input.toUpperCase().equals("Y")) {
				this.menuOptionHandler(scanner, system);
				break;
			} else if (input.toUpperCase().equals("N")) {
				this.goodbyeHandler();
				break;
			} else {
				this.invalidInputHandler();
			}
		}
	}

	// Method to handle invalid inputs and display an error

	public void invalidInputHandler() {
		System.out.println("\nInvalid Input!\n");
	}

	// -------------Goodbye handler to display goodbye message and exit the system
	// this method can be called every time we need to close the system instead of
	// writing the code repeatedly over multiple end cases

	public void goodbyeHandler() {
		System.out.println(
				"\n\nYou have chosen to exit the system! \n\nThank you for using MiRide! \n\nHave a nice day!!!");
		System.exit(0);
	}

	// Method to handle options

	public void menuOptionHandler(Scanner scanner, MiRidesSystem system) {
		this.displayOptions();

		// Scan for an input selection by the user
		System.out.print("\nPlease select an option: 	");
		// Store input into a variable
		String option = scanner.nextLine();
		// Convert to upper case so it stays valid whatever case the user inputs
		option = option.toUpperCase();

		while (true) {

			switch (option) {
			case "CC":
				this.createCar(scanner, system);
				return;
			case "BC":
				this.bookCar(scanner, system);
				return;
			case "DA":
				System.out.print("\n");
				system.getFleetDetails();
				this.goBackToMenu(scanner, system);
				return;
			case "SS":
				this.searchCar(scanner, system);
				return;
			case "EX":
				this.goodbyeHandler();
				return;
			default:
				this.invalidInputHandler();
				this.menuOptionHandler(scanner, system);
				return;
			}
		}

	}

	// Method to validate registration number

	// TODO: Error handling of invalid registration input

	public String validateRegNo(String regNo, Scanner scanner) {
		while (true) {
			if (regNo.length() != 6) {
				System.out.println("\nError: Registration Number should be 6 characters!\n");
				System.out.print("Enter Registration No:		");
				regNo = scanner.nextLine().toUpperCase();
			} else if (!regNo.substring(0, 3).matches("[a-zA-Z]+")) {
				// Check if it has anything other than alphabets
				System.out.println("\nError: First 3 characters of a registration number should only be alphabets!\n");
				System.out.print("Enter Registration No:		");
				regNo = scanner.nextLine().toUpperCase();

			} else if (!regNo.substring(3, 6).matches("[0-9]+")) {
				System.out.println("\nError: Last 3 characters of a registration number should only be numbers!\n");
				System.out.print("Enter Registration No:		");
				regNo = scanner.nextLine().toUpperCase();
			} else {
				return regNo;
			}
		}
	}

	// Method to convert string date to DateTime

	public DateTime stringToDateTime(String stringDate) {

		// Split string to day, month and year and store in an array
		String[] parts = stringDate.split("/");

		// Store the day, month and year into separate variables

		int day = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int year = Integer.parseInt(parts[2]);

		// Use DateTime method to convert to a DateTime object

		DateTime date = new DateTime(day, month, year);
		return date;
	}

	// Method to create car

	public void createCar(Scanner scanner, MiRidesSystem system) {

		// Initialise variables to store creation data before creating a car object

		String regNo;
		String make;
		String model;
		String driverName;
		int passengerCapacity;

		// Get user input to set registration number of the car

		System.out.print("\nEnter Registration No:		");
		regNo = this.validateRegNo(scanner.nextLine().toUpperCase(), scanner);

		// Validate Registration number
		// Check if registration number contains first 3 alphabets and next 3 digits

		// Get user input to set make of the car

		System.out.print("Enter Make:			");
		make = scanner.nextLine();

		// Get user input to set model of the car

		System.out.print("Enter Model:			");
		model = scanner.nextLine();

		// Get user input to set model of the car

		System.out.print("Enter Driver's Name:		");
		driverName = scanner.nextLine();

		// Get user input to set model of the car

		System.out.print("Enter Passenger Capacity:	");

		// Use parseInt to convert string to integer as when nextInt is used
		// it skips the nextLine scanner used after it

		passengerCapacity = Integer.parseInt(scanner.nextLine());

		system.createCar(regNo, make, model, driverName, passengerCapacity);

	}

	// Method to book a car
	// TODO: DateTime sets date and time
	// TODO: What if people add digits or integers into next line
	// TODO: Next int solution for scanner

	public void bookCar(Scanner scanner, MiRidesSystem system) {
		String stringDate;
		DateTime pickupDate;
		int carID;
		Car car;
		String firstName;
		String lastName;
		int numPassengers;

		// Get user input to set model of the car

		System.out.print("\nEnter Date Required:		");
		stringDate = scanner.nextLine();
		pickupDate = this.stringToDateTime(stringDate);

		System.out.println("\nThe following cars are available on this date\n");

		Car[] fleet = system.getFleet();
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {
				System.out.println((i + 1) + " " + fleet[i].getRegNo());
			}
		}

		System.out.print("\nPlease select the number next to the car you wish to book: ");
		
		// Subtract 1 as array starts from 0
		
		carID = (Integer.parseInt(scanner.nextLine())-1);
		car = fleet[carID];

		// Get user input to set first name for booking

		System.out.print("\nEnter First Name:		");
		firstName = scanner.nextLine();

		// Get user input to set last name for booking

		System.out.print("Enter Last Name:		");
		lastName = scanner.nextLine();

		// Get user input to set model of the car

		System.out.print("Enter Number of Passengers:	");

		// Use parseInt to convert string to integer as when nextInt is used
		// it skips the nextLine scanner used after it
		numPassengers = Integer.parseInt(scanner.nextLine());

		car.book(firstName, lastName, pickupDate, numPassengers);
		System.out.println("\n" + car.book.getDetails());
		
		this.goBackToMenu(scanner, system);
	}

	// Method to complete a booking

	// Method to search a specific car

	public void searchCar(Scanner scanner, MiRidesSystem system) {

		// Validate registration number
		System.out.print("\nEnter Registration No:		");
		String regNo = this.validateRegNo(scanner.nextLine().toUpperCase(), scanner);

		// Call car fleet from MiRidesSystem
		Car[] fleet = system.getFleet();

		// Iterate through the fleet array
		for (int i = 0; i < fleet.length; i++) {
			if (fleet[i] != null) {
				if (fleet[i].getRegNo().equals(regNo)) {
					System.out.println(fleet[i].getDetails());

					// Return to menu
					this.goBackToMenu(scanner, system);
				}
			}
		}
		System.out.println("\nError: The car could not be located!\n");

		// Return to menu
		this.goBackToMenu(scanner, system);
	}

	// Method to search available cars

	// Seed Data

}
