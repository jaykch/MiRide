/*
* Class: 		Menu
* Description: 	The class represents the user interface menu that handles 
* 				input validation and connects to the system.
* Author: 		Jay Kumar - S3770282
*/

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

		// ---------------------------------------------//

		// handle options from the menu
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
			case "CB":
				this.completeBooking(scanner, system);
				return;
			case "DA":
				System.out.print("\n");
				system.getFleetDetails();
				this.goBackToMenu(scanner, system);
				return;
			case "SS":
				this.searchCar(scanner, system);
				return;
			case "SA":
				this.searchAvailableCars(scanner, system);
				return;
			case "SD":
				this.seedData(scanner, system);
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

		// Use parseInt to convert string to integer as when nextInt is used
		// it skips the nextLine scanner used after it
		while (true) {
			try {
				System.out.print("Enter Passenger Capacity:	");
				passengerCapacity = Integer.parseInt(scanner.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("\nError: Please enter a valid numer!\n");
			}
		}

		// Call MiRidesSystem to create a car object
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

		// Store current date

		DateTime currentDate = new DateTime();

		while (true) {

			// Get user input to set pickup date

			System.out.print("\nEnter Date Required:		");
			stringDate = scanner.nextLine();

			try {
				pickupDate = this.stringToDateTime(stringDate);
				int timeDifference = DateTime.diffDays(pickupDate, currentDate);

				// Check to see if the booking date is not in the past or more than 1 week in
				// advance
				if (timeDifference > 0 && timeDifference <= 7) {
					break;
				} else {
					System.out.println("\nError: The date should not be in the past or more than 1 week in advance!");
				}
			} catch (Exception e) {
				System.out.println("\nError: Please enter a date in the format DD/MM/YYYY\n");
			}
		}

		while (true) {
			int maxCount = 0;

			try {
				System.out.println("\nThe following cars are available on this date\n");

				Car[] fleet = system.getFleet();
				for (int i = 0; i < fleet.length; i++) {
					if (fleet[i] != null) {
						System.out.println((i + 1) + " " + fleet[i].getRegNo());
						maxCount = i;
					}
				}

				System.out.print("\nPlease select the number next to the car you wish to book: ");

				// Subtract 1 as array starts from 0

				carID = (Integer.parseInt(scanner.nextLine()) - 1);

				if (carID <= maxCount && carID >= 0) {
					car = fleet[carID];
					break;
				}

				System.out.println("\nError: Please use the number next to the car!");

			} catch (Exception e) {
				System.out.println("\nError: Please use the number next to the car!");

			}
		}

		// Get user input to set first name for booking

		while (true) {
			System.out.print("\nEnter First Name:		");
			firstName = scanner.nextLine();

			// Check to see if at least 3 characters

			if (firstName.length() >= 3) {
				break;
			}
			System.out.println("\nError: Last name should be at least 3 characters!");
		}

		// Get user input to set last name for booking

		while (true) {
			System.out.print("Enter Last Name:		");
			lastName = scanner.nextLine();

			// Check to see if at least 3 characters

			if (lastName.length() >= 3) {
				break;
			}
			System.out.println("\nError: First name should be at least 3 characters!\n");
		}

		while (true) {
			try {
				// Get user input to set model of the car

				System.out.print("Enter Number of Passengers:	");

				// Use parseInt to convert string to integer as when nesxtInt is used
				// it skips the nextLine scanner used after it

				numPassengers = Integer.parseInt(scanner.nextLine());
				break;

			} catch (Exception e) {
				System.out.println("\nError: Please enter a valid number!\n");
			}
		}

		// Call bookCar class from MiRidesSystem to create a booking
		system.bookCar(firstName, lastName, pickupDate, numPassengers, car);
	}

	// Method to complete a booking

	public void completeBooking(Scanner scanner, MiRidesSystem system) {
		System.out.println("\nnot built yet\n");

		// Return to menu
		this.goBackToMenu(scanner, system);
	}

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

	public void searchAvailableCars(Scanner scanner, MiRidesSystem system) {
		System.out.println("\nnot built yet\n");

		// Return to menu
		this.goBackToMenu(scanner, system);
	}

	// Method to add seed data

	public void seedData(Scanner scanner, MiRidesSystem system) {
		
		// Check to see if there are cars in the fleet
		// If there are no cars there can be no bookings so don't need to check for bookings

		if (system.getFleet()[0] == null) {
			// -------Seed data---------------->

			Car beetle = new Car("BEE123", "Volkswagen", "Beetle", "Justin Beiber", 2);
			Car mustang = new Car("STA123", "Ford", "Mustang", "Post Malone", 2);
			Car ferrari = new Car("FER458", "ferarri", "458", "Ariana Grande", 2);
			Car eClass = new Car("MBE300", "Mercedes", "E-Class", "Carly Rae Jepson", 5);
			Car sClass = new Car("MBS250", "Mercedes", "S-Class", "Adolf Hitler", 4);
			Car GLE = new Car("GLE400", "Mercedes", "GLE 400", "Vlad the Impaler", 7);

			system.addCarToFleet(beetle);
			system.addCarToFleet(mustang);
			system.addCarToFleet(ferrari);
			system.addCarToFleet(eClass);
			system.addCarToFleet(sClass);
			system.addCarToFleet(GLE);

			System.out.println("\nSystem has been populated with 6 cars\n");
		} else {
			System.out.println("\nError: There are already cars in the fleet!\n");
		}

		// Return to menu
		this.goBackToMenu(scanner, system);
	}

}
