package app;

import cars.Car;
import cars.SilverServiceCar;
import utilities.DateTime;
import utilities.MiRidesUtilities;

/*
 * Class:			MiRideApplication
 * Description:		The system manager the manages the 
 *              	collection of data. 
 * Author:			Rodney Cocker
 */
public class MiRideApplication {

    private Car[] cars = new Car[15];
    private int itemCount = 0;
    private String[] availableCars;

    public MiRideApplication() {
        //seedData();
    }

    public String createCar(String id, String make, String model, String driverName, int numPassengers, String serviceType, double Fee, String[] refreshments) {
        String validId = isValidId(id);
        if (isValidId(id).contains("Error:")) {
            return validId;
        }
        if (!checkIfCarExists(id)) {
            if (serviceType.equals("SD")) {
                cars[itemCount] = new Car(id, make, model, driverName, numPassengers);
            } else {
                System.out.println("Refreshments:" + refreshments);
              
                cars[itemCount] = new SilverServiceCar(id, make, model, driverName, numPassengers, Fee, refreshments);
            }
            itemCount++;
            return "New Car added successfully for registion number: " + cars[itemCount - 1].getRegistrationNumber();
        }
        return "Error: Already exists in the system.";
    }

    public String[] book(DateTime dateRequired) {
        int numberOfAvailableCars = 0;
        // finds number of available cars to determine the size of the array required.
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (!cars[i].isCarBookedOnDate(dateRequired)) {
                    numberOfAvailableCars++;
                }
            }
        }
        if (numberOfAvailableCars == 0) {
            String[] result = new String[0];
            return result;
        }
        availableCars = new String[numberOfAvailableCars];
        int availableCarsIndex = 0;
        // Populate available cars with registration numbers
        for (int i = 0; i < cars.length; i++) {

            if (cars[i] != null) {
                if (!cars[i].isCarBookedOnDate(dateRequired)) {
                    availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getRegistrationNumber();
                    availableCarsIndex++;
                }
            }
        }
        return availableCars;
    }

    public String book(String firstName, String lastName, DateTime required, int numPassengers, String registrationNumber) {
        Car car = getCarById(registrationNumber);
        String message;
        if (car != null) {

            boolean isCarBooked = car.book(firstName, lastName, required, numPassengers);
            if (isCarBooked == true) {
                message = "Thank you for your booking. \n" + car.getDriverName()
                        + " will pick you up on " + required.getFormattedDate() + ". \n"
                        + "Your booking reference is: " + car.getBookingID(firstName, lastName, required);
                return message;
            }
            else{
                message ="Error While Booking. Please check that a SS Car can only be booked 3 days in advance";
            }
        } else {
            return "Car with registration number: " + registrationNumber + " was not found.";
        }
        return message;
    }

    public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {
        String result = "";

        // Search all cars for bookings on a particular date.
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                result = cars[i].completeBooking(firstName, lastName, dateOfBooking, kilometers);
                if (!result.equals("Booking not found")) {
                    return result;
                }
            }
        }
        return "Booking not found.";
    }

    public String completeBooking(String firstName, String lastName, String registrationNumber, double kilometers) {
        String carNotFound = "Car not found";
        Car car = null;
        // Search for car with registration number
        for (int i = 0; i < cars.length; i++) {
            if (cars[i].getRegistrationNumber().equals(registrationNumber)) {
                car = cars[i];
                break;
            }
        }

        if (car == null) {
            return carNotFound;
        }
        if (car.getBookingByName(firstName, lastName) != -1) {
            return car.completeBooking(firstName, lastName, kilometers);
        }
        return "Error: Booking not found.";
    }

    public boolean getBookingByName(String firstName, String lastName, String registrationNumber) {
        String bookingNotFound = "Error: Booking not found";
        Car car = null;
        // Search for car with registration number
        for (int i = 0; i < cars.length; i++) {
            if (cars[i].getRegistrationNumber().equals(registrationNumber)) {
                car = cars[i];
                break;
            }
        }

        if (car == null) {
            return false;
        }
        if (car.getBookingByName(firstName, lastName) == -1) {
            return false;
        }
        return false;
    }

    public String displaySpecificCar(String regNo) {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (cars[i].getRegistrationNumber().equals(regNo)) {
                    return cars[i].getDetails();
                }
            }
        }
        return "Error: The car could not be located.";
    }

    public boolean seedData() {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                return false;
            }
        }
        // 2 cars not booked
        Car honda = new Car("SIM194", "Honda", "Accord Euro", "Henry Cavill", 5);
        cars[itemCount] = honda;
        honda.book("Craig", "Cocker", new DateTime(1), 3);
        itemCount++;

        Car lexus = new Car("LEX666", "Lexus", "M1", "Angela Landsbury", 3);
        cars[itemCount] = lexus;
        lexus.book("Craig", "Cocker", new DateTime(1), 3);
        itemCount++;

        // 2 cars booked
        Car bmw = new Car("BMW256", "Mini", "Minor", "Barbara Streisand", 4);
        cars[itemCount] = bmw;
        itemCount++;
        bmw.book("Craig", "Cocker", new DateTime(1), 3);

        Car audi = new Car("AUD765", "Mazda", "RX7", "Matt Bomer", 6);
        cars[itemCount] = audi;
        itemCount++;
        audi.book("Rodney", "Cocker", new DateTime(1), 4);

        // 1 car booked five times (not available)
        Car toyota = new Car("TOY765", "Toyota", "Corola", "Tina Turner", 7);
        cars[itemCount] = toyota;
        itemCount++;
        toyota.book("Rodney", "Cocker", new DateTime(1), 3);
        toyota.book("Craig", "Cocker", new DateTime(2), 7);
        toyota.book("Alan", "Smith", new DateTime(3), 3);
        toyota.book("Carmel", "Brownbill", new DateTime(4), 7);
        toyota.book("Paul", "Scarlett", new DateTime(5), 7);
        toyota.book("Paul", "Scarlett", new DateTime(6), 7);
        toyota.book("Paul", "Scarlett", new DateTime(7), 7);

        // 1 car booked five times (not available)
        Car rover = new Car("ROV465", "Honda", "Rover", "Jonathon Ryss Meyers", 7);
        cars[itemCount] = rover;
        itemCount++;
        rover.book("Rodney", "Cocker", new DateTime(1), 3);
        //rover.completeBooking("Rodney", "Cocker", 75);
        DateTime inTwoDays = new DateTime(2);
        rover.book("Rodney", "Cocker", inTwoDays, 3);
        rover.completeBooking("Rodney", "Cocker", inTwoDays, 75);

        /*
         Method Updated To Include Silver Service Cars
         */
        String[] refreshments = {"Coke", "Chocolates", "Chips"};

        /*
         Two Cars Not Booked
         */
        SilverServiceCar car1 = new SilverServiceCar("ABC123", "Honda", "Civic", "John", 4, 3.5, refreshments);
        SilverServiceCar car2 = new SilverServiceCar("ABC124", "Honda", "City", "Marcos", 4, 4, refreshments);
        cars[itemCount] = car1;
        itemCount++;
        cars[itemCount] = car2;
        itemCount++;

        /*
         Two Cars Booked But Bookings Have Not Been Completed 
         */
        SilverServiceCar car3 = new SilverServiceCar("ABC125", "Honda", "Accord", "Zofia", 4, 5, refreshments);
        SilverServiceCar car4 = new SilverServiceCar("DEF123", "Toyota", "Corolla", "Tim", 4, 3.5, refreshments);
        cars[itemCount] = car3;
        car3.book("Craig", "Cocker", new DateTime(1), 3);
        itemCount++;
        cars[itemCount] = car4;
        car4.book("Craig", "Cocker", new DateTime(1), 3);
        itemCount++;

        /*
         Two Cars Booked And There Bookings Are Completed
         */
        SilverServiceCar car5 = new SilverServiceCar("DEF124", "Toyota", "Vitz", "Amna", 4, 4.5, refreshments);
        SilverServiceCar car6 = new SilverServiceCar("DEF125", "BMW", "X1", "Munro", 4, 5.5, refreshments);
        cars[itemCount] = car5;
        car5.book("Craig", "Cocker", new DateTime(1), 3);
        car5.completeBooking("Rodney", "Cocker", new DateTime(1), 75);
        itemCount++;
        cars[itemCount] = car6;
        car6.book("Craig", "Cocker", new DateTime(1), 3);
        car6.completeBooking("Rodney", "Cocker", new DateTime(1), 75);
        itemCount++;

        return true;
    }

    public String displayAllBookings(String sortOrder) {
        Car[] availableCarsToBook;
        boolean carsAvailable = true;
        StringBuilder sb = new StringBuilder();

        if (sortOrder.equals("D")) {
            availableCarsToBook = SortInDescendingOrder();
            if (availableCarsToBook.length == 0) {
                carsAvailable = false;
                return "No cars are available.";
            }

        } else {
            availableCarsToBook = SortInAscendingOrder();
            if (availableCarsToBook.length == 0) {
                carsAvailable = false;
                return "No cars are available.";
            }
        }
        if (carsAvailable == true) {
            for (int i = 0; i < availableCarsToBook.length; i++) {
                sb.append(availableCarsToBook[i].getDetails());
            }
        }

        return sb.toString();

    }

    public String displayBooking(String id, String seatId) {
        Car booking = getCarById(id);
        if (booking == null) {
            return "Booking not found";
        }
        return booking.getDetails();
    }

    public String isValidId(String id) {
        return MiRidesUtilities.isRegNoValid(id);
    }

    public boolean checkIfCarExists(String regNo) {
        Car car = null;
        if (regNo.length() != 6) {
            return false;
        }
        car = getCarById(regNo);
        if (car == null) {
            return false;
        } else {
            return true;
        }
    }

    private Car getCarById(String regNo) {
        Car car = null;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (cars[i].getRegistrationNumber().equals(regNo)) {
                    car = cars[i];
                    return car;
                }
            }
        }
        return car;
    }

    public String[] searchForAvailableCars(DateTime dateCarIsNeeded, String typeOfCar) {
        int numberOfAvailableCars = 0;
        // finds number of available cars to determine the size of the array required.
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (typeOfCar.equals("SS")) {
                    if ((!cars[i].isCarBookedOnDate(dateCarIsNeeded)) && (cars[i] instanceof SilverServiceCar)) {
                        numberOfAvailableCars++;
                    }
                } else {
                    if ((!cars[i].isCarBookedOnDate(dateCarIsNeeded)) && (cars[i] instanceof Car)) {
                        numberOfAvailableCars++;
                    }
                }
            }
        }
        if (numberOfAvailableCars == 0) {
            String[] result = new String[0];
            return result;
        }
        availableCars = new String[numberOfAvailableCars];
        int availableCarsIndex = 0;
        for (int i = 0; i < cars.length; i++) {

            if (cars[i] != null) {
                if (typeOfCar.equals("SS")) {
                    if ((!cars[i].isCarBookedOnDate(dateCarIsNeeded)) && (cars[i] instanceof SilverServiceCar)) {
                        availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getDetails();
                        availableCarsIndex++;
                    }
                } else {
                    if ((!cars[i].isCarBookedOnDate(dateCarIsNeeded)) && (cars[i] instanceof Car)) {
                        availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getDetails();
                        availableCarsIndex++;
                    }
                }
            }
        }
        return availableCars;
    }

    public Car[] SortInDescendingOrder() {
        boolean sorted = false;
        int numberOfCars = 0;
        Car temp;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                numberOfCars++;
            }
        }
        Car[] array = new Car[numberOfCars];

        for (int i = 0; i < array.length; i++) {
            array[i] = cars[i];
        }

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {

                String currentCarFirstPart = array[i].getRegNo().substring(0, 3);
                String nextCarFirstPart = array[i + 1].getRegNo().substring(0, 3);
                if ((currentCarFirstPart.compareTo(nextCarFirstPart) < 0)) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                }

            }
        }
        sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {

                String currentCarFirstPart = array[i].getRegNo().substring(0, 3);
                int currentCarSecondPart = Integer.parseInt(array[i].getRegNo().substring(3, 6));
                String nextCarFirstPart = array[i + 1].getRegNo().substring(0, 3);
                int nextCarSecondPart = Integer.parseInt(array[i + 1].getRegNo().substring(3, 6));
                if ((currentCarFirstPart.compareTo(nextCarFirstPart) == 0) && (currentCarSecondPart < nextCarSecondPart)) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                }

            }
        }

        return array;
    }

    public Car[] SortInAscendingOrder() {
        boolean sorted = false;
        int numberOfCars = 0;
        Car temp;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                numberOfCars++;
            }
        }
        Car[] array = new Car[numberOfCars];

        for (int i = 0; i < array.length; i++) {
            array[i] = cars[i];
        }

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {

                String currentCarFirstPart = array[i].getRegNo().substring(0, 3);
                String nextCarFirstPart = array[i + 1].getRegNo().substring(0, 3);
                if ((currentCarFirstPart.compareTo(nextCarFirstPart) > 0)) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                }

            }
        }
        sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {

                String currentCarFirstPart = array[i].getRegNo().substring(0, 3);
                int currentCarSecondPart = Integer.parseInt(array[i].getRegNo().substring(3, 6));
                String nextCarFirstPart = array[i + 1].getRegNo().substring(0, 3);
                int nextCarSecondPart = Integer.parseInt(array[i + 1].getRegNo().substring(3, 6));

                if ((currentCarFirstPart.compareTo(nextCarFirstPart) == 0) && (currentCarSecondPart > nextCarSecondPart)) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                }

            }
        }
        return array;
    }
}
