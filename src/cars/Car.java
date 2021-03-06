package cars;

import exception_handling.InvalidBooking;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;

/*
* Class: 		Car
* Description: 	This class represents a single record for any car 
* 				that can be created.
* Author: 		Jay Kumar - S3770282
*/

public class Car {

    // Car attributes
    private String regNo;
    private String make;
    private String model;
    private String driverName;
    private int passengerCapacity;
    private double bookingFee;

    // Tracking bookings
    private Booking[] currentBookings;
    private Booking[] pastBookings;
    private boolean available;
    private int bookingSpotAvailable = 0;
    private double tripFee = 0;

    // Constants
    private final double STANDARD_BOOKING_FEE = 1.5;
    private final int MAXIUM_PASSENGER_CAPACITY = 10;
    private final int MINIMUM_PASSENGER_CAPACITY = 1;

    public Car(String regNo, String make, String model, String driverName, int passengerCapacity) {
        setRegNo(regNo); // Validates and sets registration number
        setPassengerCapacity(passengerCapacity);  // Validates and sets passenger capacity

        this.make = make;
        this.model = model;
        this.driverName = driverName;
        available = true;
        currentBookings = new Booking[5];
        pastBookings = new Booking[10];
    }

    /*
     * Checks to see if the booking is permissible such as a valid date, number of passengers,
     * and general availability.
     * Creates the booking only if conditions are met and assigns the trip fee to be equal to the 
     * standard booking fee.
     */

    public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
    	
    	/*
         * ALGORITHM
         * BEGIN
         *  CHECK if car is available 
         *  CHECK if car has a booking on date requested	
         *  CHECK if the date requested is in the past. 
         *  CHECK if the number of passengers requested exceeds the capacity of the car. 
         * 	IF any checks fail
         *      return false to indicate the booking operation failed
         *  ELSE
         *      CREATE the booking
         *      ADD the booking to the current booking array 
         * 	    UPDATE the available status if there are now five current bookings.	
         * 	    RETURN true to indicate the success of the booking.	
         * END
         * 
         * TEST
         *  Booking a car to carry 0, 10, & within/without passenger capacity.
         *  Booking car on date prior to today
         *  Booking a car on a date that is more than 7 days in advance.
         *  Booking car on a date for which it is already booked
         *  Booking six cars
         */
    	
        boolean booked = false;
        // Does car have five bookings
        available = bookingAvailable();
        boolean dateAvailable = notCurrentlyBookedOnDate(required);
        // Date is within range, not in past and within the next week
        boolean dateValid = dateIsValid(required);
        // Number of passengers does not exceed the passenger capacity and is not zero.
        boolean validPassengerNumber = numberOfPassengersIsValid(numPassengers);

        if (validPassengerNumber == false) {
            throw new InvalidBooking("Number Of Passengers Exceeds The Available Capacity");
        }
        // Booking is permissible
        if (available && dateAvailable && dateValid && validPassengerNumber) {
            if (this instanceof SilverServiceCar) {
                SilverServiceCar ssCar = (SilverServiceCar) this;
                tripFee = ssCar.getBookingFee();
            } else {
                tripFee = STANDARD_BOOKING_FEE;
            }
            Booking booking = new Booking(firstName, lastName, required, numPassengers, this,true);
            currentBookings[bookingSpotAvailable] = booking;
            bookingSpotAvailable++;
            booked = true;
        }
        
        return booked;
    }
    
    /*
     * Used to setup up current bookings for cars when they are loaded from data
     * file. It skips the validation checks because we know the data will be
     * correct as it is not input by user but written my the application itself
     * on the previous run.
     */
    public void autoBook(String firstName, String lastName, DateTime required, int numPassengers) {        
        // Booking is permissible
        
            if (this instanceof SilverServiceCar) {
                SilverServiceCar ssCar = (SilverServiceCar) this;
                tripFee = ssCar.getBookingFee();
            } else {
                tripFee = STANDARD_BOOKING_FEE;
            }
            Booking booking = new Booking(firstName, lastName, required, numPassengers, this,false);
            currentBookings[bookingSpotAvailable] = booking;
            bookingSpotAvailable++;
    }

    /*
     * Completes a booking based on the name of the passenger and the booking date.
     */
    public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {
        // Find booking in current bookings by passenger and date
        int bookingIndex = getBookingByDate(firstName, lastName, dateOfBooking);
        if (bookingIndex == -1) {
            return "Booking not found.";
        }

        return completeBooking(bookingIndex, kilometers);
    }

    /*
     * Completes a booking based on the name of the passenger.
     */
    public String completeBooking(String firstName, String lastName, double kilometers) {
        int bookingIndex = getBookingByName(firstName, lastName);

        if (bookingIndex == -1) {
            return "Booking not found.";
        } else {
            return completeBooking(bookingIndex, kilometers);
        }
    }

    /*
     * Checks the current bookings to see if any of the bookings are for the current date.
     * ALGORITHM
     *   BEGIN
     *    CHECK All bookings 
     *    	IF date supplied matches date for any booking date
     *    		Return true
     *    	ELSE
     *    		Return false		
     *   END
     * 
     */
    public boolean isCarBookedOnDate(DateTime dateRequired) {
        boolean carIsBookedOnDate = false;
        if (DateUtilities.dateIsNotInPast(dateRequired) == false) {
            throw new InvalidBooking("Date Of Booking Cannot Be Prior To Current Day");
        } else if (DateUtilities.dateIsNotMoreThan7Days(dateRequired) == false) {
            throw new InvalidBooking("Date Of Booking Must Be Within The Coming Week");
        } else if (available == false || notCurrentlyBookedOnDate(dateRequired) == false) {
            throw new InvalidBooking(this.getRegNo()+" Already Has 5 Bookings");
        } else {
            for (int i = 0; i < currentBookings.length; i++) {
                if (currentBookings[i] != null) {
                    if (DateUtilities.datesAreTheSame(dateRequired, currentBookings[i].getBookingDate())) {
                        carIsBookedOnDate = true;
                    }
                }
            }
        }
        return carIsBookedOnDate;
    }

    /*
     * Retrieves a booking id based on the name and the date of the booking
     */
    public String getBookingID(String firstName, String lastName, DateTime dateOfBooking) {
        System.out.println();
        for (int i = 0; i < currentBookings.length; i++) {
            Booking booking = currentBookings[i];
            if (booking != null) {
                boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
                boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
                int days = DateTime.diffDays(dateOfBooking, booking.getBookingDate());
                if (firstNameMatch && lastNameMatch && days == 0) {
                    return booking.getID();
                }
            }
        }
        return "Booking not found";
    }

    /*
     * Human readable presentation of the state of the car.
     */
    public String getDetails() {
        StringBuilder sb = new StringBuilder();

        sb.append(getCarDetails());
        sb.append(getCurrentBookingsDetails());
        sb.append(getPastBookingsDetails());
        return sb.toString();
    }
    
    public String getCarDetails(){
        StringBuilder sb = new StringBuilder();
        sb.append(getRecordMarker());
        sb.append(String.format("%-15s %s\n", "Reg No:", this.regNo));
        sb.append(String.format("%-15s %s\n", "Make & Model:", this.make + " " + this.model));

        sb.append(String.format("%-15s %s\n", "Driver Name:", this.driverName));
        sb.append(String.format("%-15s %s\n", "Capacity:", this.passengerCapacity));
        sb.append(String.format("%-15s %s\n", "Standard Fee:", STANDARD_BOOKING_FEE));

        if (this.available) {
            sb.append(String.format("%-15s %s\n", "Available:", "YES"));
        } else {
            sb.append(String.format("%-15s %s\n", "Available:", "NO"));
        }
        return sb.toString();
    }
    
    public String getCurrentBookingsDetails(){
        StringBuilder sb = new StringBuilder();
        if(currentBookings[0] != null) {
            sb.append("CURRENT BOOKINGS\n");
        }
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] != null) {
                sb.append(currentBookings[i].getDetails());
            }
        }
        return sb.toString();
    }
    
    public String getPastBookingsDetails(){
        StringBuilder sb = new StringBuilder();
        if(pastBookings[0] != null) {
            sb.append("PAST BOOKINGS\n");
        }
        for (int i = 0; i < pastBookings.length; i++) {
            if (pastBookings[i] != null) {
                sb.append(pastBookings[i].getDetails());
            }
        }
        return sb.toString();
    }

    /*
     * Computer readable state of the car
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean currentBookingsAdded = false;
        boolean pastBookingsAdded = false;

        if (this instanceof SilverServiceCar) {
            sb.append("SS" + ":");
        } else {
            sb.append("SD" + ":");
        }
        sb.append(regNo + ":" + make + ":" + model);
        if (driverName != null) {
            sb.append(":" + driverName);
        }
        sb.append(":" + passengerCapacity);
        if (available) {
            sb.append(":" + "YES");
        } else {
            sb.append(":" + "NO");
        }
        if (this instanceof SilverServiceCar) {
            SilverServiceCar ssCar = (SilverServiceCar) this;
            sb.append(":" + ssCar.getBookingFee());
            for(int i=0;i<ssCar.getRefreshments().length;i++){
            sb.append(":" +ssCar.getRefreshments()[i]);
            }
        } else {
            sb.append(":" + STANDARD_BOOKING_FEE);
        }

        if (checkCurrentBookingsForNonNullValues() == true) {
            currentBookingsAdded = true;
            sb.append("--%--");
            for (int i = 0; i < currentBookings.length; i++) {
                if (currentBookings[i] != null) {
                    sb.append("|" + currentBookings[i].toString());
                }
            }
        }

        if (currentBookingsAdded == true) {
            sb.append("--%--");
        }

        if (checkPastBookingsForNonNullValues() == true) {
            pastBookingsAdded = true;
            sb.append("--$--");
            for (int i = 0; i < pastBookings.length; i++) {
                if (pastBookings[i] != null) {
                    sb.append("|" + pastBookings[i].toString());
                }
            }
        }

        if (pastBookingsAdded == true) {
            sb.append("--$--");
        }
        return sb.toString();
    }

    // Required getters
    public String getRegistrationNumber() {
        return regNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getTripFee() {
        return tripFee;
    }

    /*
     * Processes the completion of the booking
     */
    private String completeBooking(int bookingIndex, double kilometers) {
        Booking booking = currentBookings[bookingIndex];
        double fee;
        // Remove booking from current bookings array.
        currentBookings[bookingIndex] = null;

        // call complete booking on Booking object
        //double kilometersTravelled = Math.random()* 100;
        if (this instanceof SilverServiceCar) {
            SilverServiceCar silverServiceCar = (SilverServiceCar) this;
            fee = kilometers * (silverServiceCar.getBookingFee() * 0.4);
            tripFee += fee;
            booking.completeBooking(kilometers, fee, silverServiceCar.getBookingFee());
        } else {
            fee = kilometers * (STANDARD_BOOKING_FEE * 0.3);
            tripFee += fee;
            booking.completeBooking(kilometers, fee, STANDARD_BOOKING_FEE);
        }
        // add booking to past bookings
        for (int i = 0; i < pastBookings.length; i++) {
            if (pastBookings[i] == null) {
                pastBookings[i] = booking;
                break;
            }
        }
        String result = String.format("Thank you for riding with MiRide.\nWe hope you enjoyed your trip.\n$"
                + "%.2f has been deducted from your account.", tripFee);
        return result;
    }
    
    /*
     * Used to setup up past bookings for cars when they are loaded from data
     * file. It skips the validation checks because we know the data will be
     * correct as it is not input by user but written my the application itself
     * on the previous run.
     */
    public void autoCompleteBooking(Booking booking,double kilometers,double tripFee,double bookingFee) {
        
        booking.completeBooking(kilometers, tripFee, bookingFee);
        
        // add booking to past bookings
        for (int i = 0; i < pastBookings.length; i++) {
            if (pastBookings[i] == null) {
                pastBookings[i] = booking;
                break;
            }
        }
    }

    /*
     * Gets the position in the array of a booking based on a name and date.
     * Returns the index of the booking if found.
     * Otherwise it returns -1 to indicate the booking was not found.
     */
    private int getBookingByDate(String firstName, String lastName, DateTime dateOfBooking) {
        System.out.println();
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] != null) {
                Booking booking = currentBookings[i];
                boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
                boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
                boolean dateMatch = DateUtilities.datesAreTheSame(dateOfBooking, currentBookings[i].getBookingDate());
                if (firstNameMatch && lastNameMatch && dateMatch) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
     * Gets the position in the array of a booking based on a name.
     * Returns the index of the booking if found.
     * Otherwise it returns -1 to indicate the booking was not found.
     */
    public int getBookingByName(String firstName, String lastName) {
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] != null) {
                boolean firstNameMatch = currentBookings[i].getFirstName().toUpperCase().equals(firstName.toUpperCase());
                boolean lastNameMatch = currentBookings[i].getLastName().toUpperCase().equals(lastName.toUpperCase());
                if (firstNameMatch && lastNameMatch) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
     * A record marker mark the beginning of a record.
     */
    private String getRecordMarker() {
        final int RECORD_MARKER_WIDTH = 60;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < RECORD_MARKER_WIDTH; i++) {
            sb.append("_");
        }
        sb.append("\n");
        return sb.toString();
    }

    /*
     * Checks to see if the number of passengers falls within the accepted range.
     */
    private boolean numberOfPassengersIsValid(int numPassengers) {
        if (numPassengers >= MINIMUM_PASSENGER_CAPACITY
                && numPassengers < MAXIUM_PASSENGER_CAPACITY
                && numPassengers <= passengerCapacity) {
            return true;
        }
        return false;
    }

    /*
     * Checks that the date is not in the past or more than 7 days in the future.
     */
    private boolean dateIsValid(DateTime date) {
        return DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan7Days(date);
    }

    /*
     * Indicates if a booking spot is available.
     * If it is then the index of the available spot is assigned to 
     * bookingSpotFree.
     */
    private boolean bookingAvailable() {
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] == null) {
                bookingSpotAvailable = i;
                return true;
            }
        }
        return false;
    }

    /*
     * Checks to see if if the car is currently booked on the date specified.
     */
    private boolean notCurrentlyBookedOnDate(DateTime date) {
        boolean foundDate = true;
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] != null) {
                int days = DateTime.diffDays(date, currentBookings[i].getBookingDate());
                if (days == 0) {
                    return false;
                }
            }
        }
        return foundDate;
    }

    /*
     * Validates and sets the registration number
     */
    private void setRegNo(String regNo) {
        if (!MiRidesUtilities.isRegNoValid(regNo).contains("Error:")) {
            this.regNo = regNo;
        } else {
            this.regNo = "Invalid";
        }
    }

    /*
     * Validates and sets the passenger capacity
     */
    private void setPassengerCapacity(int passengerCapacity) {
        boolean validPasengerCapcity = passengerCapacity >= MINIMUM_PASSENGER_CAPACITY
                && passengerCapacity < MAXIUM_PASSENGER_CAPACITY;

        if (validPasengerCapcity) {
            this.passengerCapacity = passengerCapacity;
        } else {
            this.passengerCapacity = -1;
        }
    }

    public String getRegNo() {
        return regNo;
    }

    public double getSTANDARD_BOOKING_FEE() {
        return STANDARD_BOOKING_FEE;
    }
    

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getBookingSpotAvailable() {
        return bookingSpotAvailable;
    }

    public boolean checkCurrentBookingsForNonNullValues() {
        for (int i = 0; i < currentBookings.length; i++) {
            if (currentBookings[i] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPastBookingsForNonNullValues() {
        for (int i = 0; i < pastBookings.length; i++) {
            if (pastBookings[i] != null) {
                return true;
            }
        }
        return false;
    }
    public double getBookingFee() {
        return bookingFee;
    }

    protected void setBookingFee(double bookingFee) {
        this.bookingFee = bookingFee;
    }
}
