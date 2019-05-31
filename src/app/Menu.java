package app;

import exception_handling.InvalidBooking;
import exception_handling.InvalidCarServiceType;
import exception_handling.InvalidDate;
import exception_handling.InvalidRefreshments;
import exception_handling.InvalidRegID;
import exception_handling.SilverServiceCarMinimumBookingFee;
import java.util.Scanner;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;

/*
 * Class:		Menu
 * Description:	The class a menu and is used to interact with the user. 
 * Author:		Jay Kumar
 */
public class Menu {

    private Scanner console = new Scanner(System.in);
    private MiRideApplication application = new MiRideApplication();
    // Allows me to turn validation on/off for testing business logic in the
    // classes.
    private boolean testingWithValidation = true;

    /*
     * Runs the menu in a loop until the user decides to exit the system.
     */
    public void run() {
        final int MENU_ITEM_LENGTH = 2;
        String input;
        String choice = "";
        try {
            application.restoreDataFromTextFile();
            do {
                printMenu();

                input = console.nextLine().toUpperCase();

                if (input.length() != MENU_ITEM_LENGTH) {
                    System.out.println("Error - selection must be two characters!");
                } else {
                    System.out.println();

                    switch (input) {
                        case "CC":
                            createCar();
                            break;
                        case "BC":
                            book();
                            break;
                        case "CB":
                            completeBooking();
                            break;
                        case "DA":

                            System.out.println("Enter Car Type (SD/SS): ");
                            String serviceType = console.nextLine().toUpperCase();
                            System.out.println("Enter Sort Order (A/D): ");
                            String sortOrder = console.nextLine().toUpperCase();

                            System.out.println(application.displayAllBookings(sortOrder,serviceType));
                            break;
                        case "SS":
                            System.out.print("Enter Registration Number: ");
                            System.out.println(application.displaySpecificCar(console.nextLine()));
                            break;
                        case "SD":
                            application.seedData();
                            break;
                        case "SC":
                            SearchForAvailableCars();
                            break;
                        case "EX":
                            System.out.println("Exiting Program ... Goodbye!");
                            System.out.println("");
                            application.writeToTextFile();
                            System.exit(0);

                        default:
                            System.out.println("Error, invalid option selected!");
                            System.out.println("Please try Again...");
                    }
                }

            } while (choice != "EX");
        } catch (InvalidRegID e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Creates cars for use in the system available or booking.
     */
    private void createCar() {

        String id = "", make, model, driverName, serviceType, listOfRefreshments = "";
        double standardFee = 3;
        int numPassengers = 0;
        boolean isSilverServiceCar = false;

        try {
            System.out.print("Enter registration number: ");
            id = promptUserForRegNo();
            if (id.length() != 0) {
                // Get details required for creating a car.
                System.out.print("Enter Make: ");
                make = console.nextLine();

                System.out.print("Enter Model: ");
                model = console.nextLine();

                System.out.print("Enter Driver Name: ");
                driverName = console.nextLine();

                System.out.print("Enter number of passengers: ");
                numPassengers = Integer.parseInt(console.nextLine());

                System.out.println("Enter Service Type (SD/SS): ");
                serviceType = console.nextLine().toUpperCase();

                if (serviceType.equals("SD")) {
                    isSilverServiceCar = false;
                } else if (serviceType.equals("SS")) {
                    System.out.println("Enter Standard Fee: ");
                    standardFee = Double.parseDouble(console.nextLine());
                    System.out.println("Enter List Of Refreshments (Seperate with ,):");
                    listOfRefreshments = console.nextLine();
                    isSilverServiceCar = true;
                } else {
                    throw new InvalidCarServiceType("Car Service Type Can Only Be SS or SD");
                }
                boolean result = application.checkIfCarExists(id);

                if (!result) {
                    if (isSilverServiceCar == false) {
                        String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers, serviceType, -1, null);
                        System.out.println(carRegistrationNumber);
                    } else {
                        String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers, serviceType, standardFee, listOfRefreshments.split(","));
                        System.out.println(carRegistrationNumber);
                    }
                } else {
                    System.out.println("Error - Already exists in the system");
                }
            }
        } catch (InvalidRefreshments | InvalidRegID | InvalidCarServiceType | SilverServiceCarMinimumBookingFee e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Number Of Passengers Must Be An Integer Value. Standard Fee Must Be A Double");
        }
    }

    /*
     * Book a car by finding available cars for a specified date.
     */
    private boolean book() {
        try {
            System.out.println("Enter date car required: ");
            System.out.println("format DD/MM/YYYY)");
            String dateEntered = console.nextLine();
            if (DateUtilities.CheckDateFormat(dateEntered) == false) {
                throw new InvalidDate("Wrong Date or Wrong Format.Enter Date In The Following Format DD/MM/YYYY");
            }
            int day = Integer.parseInt(dateEntered.substring(0, 2));
            int month = Integer.parseInt(dateEntered.substring(3, 5));
            int year = Integer.parseInt(dateEntered.substring(6));
            DateTime dateRequired = new DateTime(day, month, year);

            String[] availableCars = application.book(dateRequired);
            for (int i = 0; i < availableCars.length; i++) {
                System.out.println(availableCars[i]);
            }
            if (availableCars.length != 0) {
                System.out.println("Please enter a number from the list:");
                int itemSelected = Integer.parseInt(console.nextLine());

                String regNo = availableCars[itemSelected - 1];
                regNo = regNo.substring(regNo.length() - 6);
                System.out.println("Please enter your first name:");
                String firstName = console.nextLine();
                System.out.println("Please enter your last name:");
                String lastName = console.nextLine();
                System.out.println("Please enter the number of passengers:");
                int numPassengers = Integer.parseInt(console.nextLine());
                String result = application.book(firstName, lastName, dateRequired, numPassengers, regNo);

                System.out.println(result);
            } else {
                System.out.println("There are no available cars on this date.");
            }
        } catch (InvalidBooking | InvalidDate e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /*
     * Complete bookings found by either registration number or booking date.
     */
    private void completeBooking() {
        System.out.print("Enter Registration or Booking Date:");
        String response = console.nextLine();

        String result = "";
        try {
            // User entered a booking date
            if (response.contains("/")) {
                if (DateUtilities.CheckDateFormat(response) == false) {
                    throw new InvalidDate("Wrong Date. Format Should be DD/MM/YYYY");
                }

                System.out.print("Enter First Name:");
                String firstName = console.nextLine();
                System.out.print("Enter Last Name:");
                String lastName = console.nextLine();
                System.out.print("Enter kilometers:");
                double kilometers = Double.parseDouble(console.nextLine());
                int day = Integer.parseInt(response.substring(0, 2));
                int month = Integer.parseInt(response.substring(3, 5));
                int year = Integer.parseInt(response.substring(6));
                DateTime dateOfBooking = new DateTime(day, month, year);
                result = application.completeBooking(firstName, lastName, dateOfBooking, kilometers);
                System.out.println(result);
            } else {
                MiRidesUtilities.isRegNoValid(result);

                System.out.print("Enter First Name:");
                String firstName = console.nextLine();
                System.out.print("Enter Last Name:");
                String lastName = console.nextLine();
                if (application.getBookingByName(firstName, lastName, response)) {
                    System.out.print("Enter kilometers:");
                    double kilometers = Double.parseDouble(console.nextLine());
                    result = application.completeBooking(firstName, lastName, response, kilometers);
                    System.out.println(result);
                } else {
                    System.out.println("Error: Booking not found.");
                }
            }
        } catch (InvalidDate | InvalidRegID e) {
            System.out.println(e.getMessage());
        }

    }

    /*
     * Prompt user for registration number and validate it is in the correct form.
     * Boolean value for indicating test mode allows by passing validation to test
     * program without user input validation.
     */
    private String promptUserForRegNo() {
        String regNo = "";
        boolean validRegistrationNumber = false;
        // By pass user input validation.
        if (!testingWithValidation) {
            return console.nextLine();
        } else {
            while (!validRegistrationNumber) {
                regNo = console.nextLine().toUpperCase();
                boolean exists = application.checkIfCarExists(regNo);
                if (exists) {
                    // Empty string means the menu will not try to process
                    // the registration number
                    System.out.println("Error: Reg Number already exists");
                    return "";
                }
                if (regNo.length() == 0) {
                    break;
                }

                String validId = application.isValidId(regNo);
                if (validId.contains("Error:")) {
                    System.out.println(validId);
                    System.out.println("Enter registration number: ");
                    System.out.println("(or hit ENTER to exit)");
                } else {
                    validRegistrationNumber = true;
                }
            }
            return regNo;
        }
    }

    /*
     * Search for All Available Cars in Application.
     * Searching is done on basis of date which checks if car is available on the
     * specific date and the type of car.
     */
    private boolean SearchForAvailableCars() {
        try {
            System.out.print("Enter Date Car is Needed (Format DD/MM/YYYY):");
            String dateNeeded = console.nextLine();
            if (DateUtilities.CheckDateFormat(dateNeeded) == false) {
                throw new InvalidDate("Wrong Date. Format should be DD/MM/YYYY");
            }
            int day = Integer.parseInt(dateNeeded.substring(0, 2));
            int month = Integer.parseInt(dateNeeded.substring(3, 5));
            int year = Integer.parseInt(dateNeeded.substring(6));
            DateTime dateRequired = new DateTime(day, month, year);

            System.out.print("Enter Type Of Car (SD/SS): ");
            String typeOfCar = console.nextLine();

            if (!(typeOfCar.equals("SD") | typeOfCar.equals("SS"))) {
                throw new InvalidCarServiceType("Car Service Type Can Only Be SS or SD");
            }

            String[] availableCars = application.searchForAvailableCars(dateRequired, typeOfCar);
            if (availableCars.length == 0) {
                System.out.println("No Cars Available Matching Your Criteria ");
            } else {
                for (int i = 0; i < availableCars.length; i++) {
                    System.out.println(availableCars[i]);
                }
            }
        } catch (InvalidDate | InvalidCarServiceType e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /*
     * Prints the menu.
     */
    private void printMenu() {
        System.out.printf("\n********** MiRide System Menu **********\n\n");

        System.out.printf("%-30s %s\n", "Create Car", "CC");
        System.out.printf("%-30s %s\n", "Book Car", "BC");
        System.out.printf("%-30s %s\n", "Complete Booking", "CB");
        System.out.printf("%-30s %s\n", "Display ALL Cars", "DA");
        System.out.printf("%-30s %s\n", "Search Specific Car", "SS");
        System.out.printf("%-30s %s\n", "Search Available Cars", "SA");
        System.out.printf("%-30s %s\n", "Seed Data", "SD");
        System.out.printf("%-30s %s\n", "Search For Available Cars:", "SC");
        System.out.printf("%-30s %s\n", "Exit Program", "EX");
        System.out.println("\nEnter your selection: ");
        System.out.println("(Hit enter to cancel any operation)");
    }
}
