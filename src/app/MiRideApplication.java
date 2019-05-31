package app;

import cars.Booking;
import cars.Car;
import cars.SilverServiceCar;
import exception_handling.InvalidBooking;
import exception_handling.InvalidCarServiceType;
import exception_handling.InvalidSortOrder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DateTime;
import utilities.MiRidesUtilities;

/*
 * Class:			MiRideApplication
 * Description:		The system manager the manages the 
 *              	collection of data. 
 * Author:			Jay Kumar
 */
public class MiRideApplication {

    private Car[] cars = new Car[15];

    private int itemCount = 0;
    private String[] availableCars;

    public MiRideApplication() {
        //seedData();
    }

    /*
     * Creates cars for use in the system available or booking.
     */
    public String createCar(String id, String make, String model, String driverName, int numPassengers, String serviceType, double Fee, String[] refreshments) {
        String validId = isValidId(id);
        if (isValidId(id).contains("Error:")) {
            return validId;
        }
        if (!checkIfCarExists(id)) {
            if (serviceType.equals("SD")) {
                cars[itemCount] = new Car(id, make, model, driverName, numPassengers);
            } else {
                cars[itemCount] = new SilverServiceCar(id, make, model, driverName, numPassengers, Fee, refreshments);
            }
            itemCount++;
            return "New Car added successfully for registion number: " + cars[itemCount - 1].getRegistrationNumber();
        }
        return "Error: Already exists in the system.";
    }

    /*
     * Is used to get the cars available on the booking date specified by user.
     */
    public String[] book(DateTime dateRequired) {
        int numberOfAvailableCars = 0;
        boolean bookingValid = true;

        // finds number of available cars to determine the size of the array required.
        for (int i = 0; i < cars.length; i++) {
            try {
                if (cars[i] != null) {
                    if (!cars[i].isCarBookedOnDate(dateRequired)) {
                        numberOfAvailableCars++;
                    }
                }
            }catch(InvalidBooking e){
                System.out.println(e.getMessage());
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
            try {
                if (cars[i] != null) {
                    if (!cars[i].isCarBookedOnDate(dateRequired)) {
                        availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getRegistrationNumber();
                        availableCarsIndex++;
                    }
                }
            } catch (InvalidBooking e) {
                e.getMessage();
            }

        }

        return availableCars;
    }

    /*
     * Performs booking of the selected car by user on selected date
     */
    public String book(String firstName, String lastName, DateTime required, int numPassengers, String registrationNumber) {

        Car car = getCarById(registrationNumber);
        String message = "";

        if (car != null) {

            boolean isCarBooked = car.book(firstName, lastName, required, numPassengers);
            if (isCarBooked == true) {
                message = "Thank you for your booking. \n" + car.getDriverName()
                        + " will pick you up on " + required.getFormattedDate() + ". \n"
                        + "Your booking reference is: " + car.getBookingID(firstName, lastName, required);
                return message;
            } else {
                message = "Error While Booking. Please check that a SS Car can only be booked 3 days in advance";
            }
        } else {
            return "Car with registration number: " + registrationNumber + " was not found.";
        }

        return message;
    }

    /*
     * Searches For Booking To Complete
     */
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

    /*
     * Complete Booking For Car
     */
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

    /*
     * Get Booking by using carRegistration Numer
     */
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

    /*
     * Used to display specific car information. SS function on menu
     */
    public String displaySpecificCar(String regNo) {
        MiRidesUtilities.isRegNoValid(regNo);
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (cars[i].getRegistrationNumber().equals(regNo)) {
                    return cars[i].getDetails();
                }
            }
        }
        return "Error: The car could not be located.";
    }

    /*
     * Populates application with data.
     */
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

    /*
     * Displays All Bookings in Ascending/Descending Order as specified by user
     */
    public String displayAllBookings(String sortOrder, String carType) {
        Car[] availableCarsToBook;
        boolean carsAvailable = true;
        StringBuilder sb = new StringBuilder();
        try {
            if (!(carType.equals("SD") | carType.equals("SS"))) {
                throw new InvalidCarServiceType("Car Service Type Can Only Be SS or SD");
            }
            if (!(sortOrder.equals("D") | sortOrder.equals("A"))) {
                throw new InvalidSortOrder("Car Service Type Can Only Be SS or SD");
            }

            if (sortOrder.equals("D")) {
                availableCarsToBook = SortInDescendingOrder(carType);
                if (availableCarsToBook.length == 0) {
                    carsAvailable = false;
                    return "No cars are available.";
                }

            } else {
                availableCarsToBook = SortInAscendingOrder(carType);
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
        } catch (InvalidCarServiceType | InvalidSortOrder e) {
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }

    /*
     * Displays booking info using booking ID
     */
    public String displayBooking(String id, String seatId) {
        Car booking = getCarById(id);
        if (booking == null) {
            return "Booking not found";
        }
        return booking.getDetails();
    }

    /*
     * Checks If Registration Number is valid.
     */
    public String isValidId(String id) {
        return MiRidesUtilities.isRegNoValid(id);
    }

    /*
     * Checks If Car Is Present in syste using registration number
     */
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

    /*
     * Get Car By ID
     */
    private Car getCarById(String regNo) {
        //Car car = null;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (cars[i].getRegistrationNumber().equals(regNo)) {
                    return cars[i];
                }
            }
        }
        return null;
    }

    /*
     * Searches For Available Cars. SA functionality in menu
     */
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

    /*
     * This method sorts the cars to display in Descending order. Used when
     * displaying all bookings.
     */
    public Car[] SortInDescendingOrder(String carType) {
        boolean sorted = false;
        int numberOfCars = 0;
        int puttingIndex = 0;
        Car temp;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (carType.equals("SS")) {
                    if (cars[i] instanceof SilverServiceCar) {
                        numberOfCars++;
                    }
                } else if (carType.equals("SD")) {
                    if (!(cars[i] instanceof SilverServiceCar)) {
                        numberOfCars++;
                    }
                }

            }
        }
        Car[] array = new Car[numberOfCars];

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (carType.equals("SS")) {
                    if (cars[i] instanceof SilverServiceCar) {
                        array[puttingIndex] = cars[i];
                        puttingIndex++;
                    }
                } else if (carType.equals("SD")) {
                    if (!(cars[i] instanceof SilverServiceCar)) {
                        array[puttingIndex] = cars[i];
                        puttingIndex++;
                    }
                }
            }

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

    /*
     * This method sorts the cars to display in Ascending order. Used when
     * displaying all bookings.
     */
    public Car[] SortInAscendingOrder(String carType) {
        boolean sorted = false;
        int numberOfCars = 0;
        int puttingIndex = 0;
        Car temp;

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (carType.equals("SS")) {
                    if (cars[i] instanceof SilverServiceCar) {
                        numberOfCars++;
                    }
                } else if (carType.equals("SD")) {
                    if (!(cars[i] instanceof SilverServiceCar)) {
                        numberOfCars++;
                        System.out.println("Number Of Cars: " + numberOfCars);
                    }
                }

            }
        }
        Car[] array = new Car[numberOfCars];

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (carType.equals("SS")) {
                    if (cars[i] instanceof SilverServiceCar) {
                        array[puttingIndex] = cars[i];
                        puttingIndex++;
                    }
                } else if (carType.equals("SD")) {
                    if (!(cars[i] instanceof SilverServiceCar)) {
                        array[puttingIndex] = cars[i];
                        puttingIndex++;
                    }
                }
            }
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

    /*
     * Returns all cars currently in system.
     */
    public Car[] getCars() {
        return cars;
    }

    /*
     * Get All Data From application to write to data file
     */
    public String getDataToWrite() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                sb.append(cars[i].toString() + "\n");
            }
        }
        return sb.toString();
    }

    /*
     * Writes the application data to data file on Exit
     */
    public void writeToTextFile() {
        FileWriter dataFileFileWriter = null;
        FileWriter backupDataFileFileWriter = null;
        PrintWriter printWriter = null;
        removeOldFileBeforeWriting();
        try {
            dataFileFileWriter = new FileWriter("data.txt");
            backupDataFileFileWriter = new FileWriter("backup_data.txt");
            String dataToWrite = getDataToWrite();
            printWriter = new PrintWriter(dataFileFileWriter);
            printWriter.print(dataToWrite);
            printWriter.close();
            printWriter = new PrintWriter(backupDataFileFileWriter);
            printWriter.print(dataToWrite);
            printWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataFileFileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * Remove Old Data Before Writing New.
     */
    public void removeOldFileBeforeWriting() {
        File dataFile = new File("data.txt");
        File backupDataFile = new File("backup_data.txt");
        if (dataFile.exists()) {
            dataFile.delete();
        }
        if (backupDataFile.exists()) {
            backupDataFile.delete();
        }
    }

    /*
     * Restore Data from original data file at start of application
     */
    public void restoreDataFromTextFile() {
        BufferedReader br = null;
        boolean fileFound = true;
        try {
            File file = new File("data.txt");
            br = new BufferedReader(new FileReader(file));
            if (fileFound == true) {
                String st;
                while ((st = br.readLine()) != null) {
                    parseRecord(st);
                }
            }

        } catch (FileNotFoundException ex) {
            fileFound = false;
            System.out.println("Data File Not Found. Trying to load from Backup Data File");
            restoreDataFromBackupTextFile();
            //Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fileFound == true) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * Restore Data From Backup Text File. Used If original data file not found
     */
    public void restoreDataFromBackupTextFile() {
        BufferedReader br = null;
        boolean fileFound = true;
        try {
            File file = new File("backup_data.txt");
            br = new BufferedReader(new FileReader(file));
            if (fileFound == true) {
                String st;
                while ((st = br.readLine()) != null) {
                    parseRecord(st);
                }
                System.out.println("Data Successfully Read From Backup Data File");
            }

        } catch (FileNotFoundException ex) {
            fileFound = false;
            System.out.println("Backup Data File Not Found. Starting Application Without Loading Data");
        } catch (IOException ex) {
            Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fileFound == true) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MiRideApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * Parses the record stored in the data file/backup data file so that it can
     * be inserted into the system.
     */
    public void parseRecord(String record) {
        String actualRecord = "";
        boolean currentBookingDelimiterFound = false;
        String currentBookingsDelimiter = "--%--";
        String pastBookingsDelimiter = "--$--";
        int startIndexOfCurrentBookingDelimiter = 0;
        int endIndexOfCurrentBookingDelimiter;
        int startIndexOfPastBookingDelimiter;
        int endIndexOfPastBookingDelimiter;
        String[] pastBookings = null;
        String[] currentBookings = null;
        if (record.contains(currentBookingsDelimiter) == false && record.contains(pastBookingsDelimiter) == false) {
            actualRecord = record;
        } else {
            if (record.contains(currentBookingsDelimiter)) {
                currentBookingDelimiterFound = true;
                startIndexOfCurrentBookingDelimiter = record.indexOf(currentBookingsDelimiter);
                endIndexOfCurrentBookingDelimiter = record.lastIndexOf(currentBookingsDelimiter);

                actualRecord = record.substring(0, startIndexOfCurrentBookingDelimiter);
                currentBookings = record.substring(startIndexOfCurrentBookingDelimiter + currentBookingsDelimiter.length(), endIndexOfCurrentBookingDelimiter).split("\\|");

            }
            if (record.contains(pastBookingsDelimiter)) {
                startIndexOfPastBookingDelimiter = record.indexOf(pastBookingsDelimiter);
                endIndexOfPastBookingDelimiter = record.lastIndexOf(pastBookingsDelimiter);

                if (currentBookingDelimiterFound == true) {
                    actualRecord = record.substring(0, startIndexOfCurrentBookingDelimiter);
                } else {
                    actualRecord = record.substring(0, startIndexOfPastBookingDelimiter);
                }
                pastBookings = record.substring(startIndexOfPastBookingDelimiter + currentBookingsDelimiter.length(), endIndexOfPastBookingDelimiter).split("\\|");
            }
        }
        loadAllDataIntoSystem(actualRecord, currentBookings, pastBookings);
    }

    /*
     * Loads the parsed data into the system
     */
    public void loadAllDataIntoSystem(String record, String[] currentBookings, String[] pastBookings) {
        if (currentBookings == null && pastBookings == null) {
            loadCar(record);
        } else {
            loadCar(record);
            if (currentBookings != null) {
                loadCurrentBookings(record, currentBookings);
            }
            if (pastBookings != null) {
                loadPastBookings(record, pastBookings);
            }

        }

    }

    /*
     * Loads parsed car record into system.
     */
    public void loadCar(String record) {
        String[] temp = record.split(":");
        if (temp[0].equals("SD")) {
            createCar(temp[1], temp[2], temp[3], temp[4], Integer.parseInt(temp[5]), temp[0], -1, null);
        } else {
            int startIndexOfRefreshments = 8;
            String[] refreshments = new String[temp.length - startIndexOfRefreshments];
            for (int i = 0; i < refreshments.length; i++) {
                refreshments[i] = temp[startIndexOfRefreshments];
                startIndexOfRefreshments++;
            }
            createCar(temp[1], temp[2], temp[3], temp[4], Integer.parseInt(temp[5]), temp[0], Double.parseDouble(temp[7]), refreshments);
        }
    }

    /*
     * Loads parsed current booking records for specific cars into system.
     */
    public void loadCurrentBookings(String record, String[] currentBookings) {
        String[] temp_record = record.split(":");
        String[] temp_all_current_bookings = cleanBookingArrays(currentBookings);

        for (int i = 0; i < temp_all_current_bookings.length; i++) {
            String[] temp_current_booking = temp_all_current_bookings[i].split(":");
            String[] name_of_booker = temp_current_booking[3].split(" ");
            int day = Integer.parseInt(temp_current_booking[2].substring(0, 2));
            int month = Integer.parseInt(temp_current_booking[2].substring(2, 4));
            int year = Integer.parseInt(temp_current_booking[2].substring(4));

            if (temp_record[0].equals("SD")) {
                Car sdCar = getCarById(temp_record[1]);
                sdCar.autoBook(name_of_booker[0], name_of_booker[0], new DateTime(day, month, year), Integer.parseInt(temp_current_booking[4]));
            } else {
                SilverServiceCar ssCar = (SilverServiceCar) getCarById(temp_record[1]);
                ssCar.autoBook(name_of_booker[0], name_of_booker[0], new DateTime(day, month, year), Integer.parseInt(temp_current_booking[4]));
            }
        }
    }

    /*
     * Loads parsed past booking records into system.
     */
    public void loadPastBookings(String record, String[] pastBookings) {
        String[] temp_record = record.split(":");
        String[] temp_all_past_bookings = cleanBookingArrays(pastBookings);

        for (int i = 0; i < temp_all_past_bookings.length; i++) {
            String[] temp_past_booking = temp_all_past_bookings[i].split(":");
            String[] name_of_booker = temp_past_booking[3].split(" ");

            int day = Integer.parseInt(temp_past_booking[2].substring(0, 2));
            int month = Integer.parseInt(temp_past_booking[2].substring(2, 4));
            int year = Integer.parseInt(temp_past_booking[2].substring(4));

            if (temp_record[0].equals("SD")) {
                Car sdCar = getCarById(temp_record[1]);
                //sdCar.autoBook(name_of_booker[0], name_of_booker[0], new DateTime(day, month, year), Integer.parseInt(temp_current_booking[4]));
                Booking booking = new Booking(name_of_booker[0], name_of_booker[0], new DateTime(day, month, year), Integer.parseInt(temp_past_booking[4]), sdCar, false);
                sdCar.autoCompleteBooking(booking, Double.parseDouble(temp_past_booking[5]), Double.parseDouble(temp_past_booking[6]), sdCar.getSTANDARD_BOOKING_FEE());

            } else {
                SilverServiceCar ssCar = (SilverServiceCar) getCarById(temp_record[1]);
                Booking booking = new Booking(name_of_booker[0], name_of_booker[0], new DateTime(day, month, year), Integer.parseInt(temp_past_booking[4]), ssCar, false);
                ssCar.autoCompleteBooking(booking, Double.parseDouble(temp_past_booking[5]), Double.parseDouble(temp_past_booking[6]), ssCar.getBookingFee());

            }
        }
    }

    /*
     * A helper function to clean the bookings arrays when parsed
     */
    private String[] cleanBookingArrays(String[] array) {
        String[] temp_array;
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("")) {
                count++;
            }
        }
        temp_array = new String[count];
        count = 0;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("")) {
                temp_array[count] = array[i];
                count++;
            }
        }
        return temp_array;
    }

}
