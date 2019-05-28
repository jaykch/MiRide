package cars;

import utilities.DateTime;
import utilities.DateUtilities;

public class SilverServiceCar extends Car {

    // Silver service car attributes
    private double bookingFee;
    private String[] refreshments;

    // Constants
    private final double MINIMUM_BOOKING_FEE = 3.0;

    public SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
            double bookingFee, String[] refreshments) {
        super(regNo, make, model, driverName, passengerCapacity);
        //If Booking Fee Is Less Than 3 Then Make it equal to the minimum booking fee 
        if (bookingFee < MINIMUM_BOOKING_FEE) {
            System.out.println("Minimum Fee For SS Car Cannot Be Less Than 3. Setting Fee Equal To 3");
            this.bookingFee = MINIMUM_BOOKING_FEE;
        } else {
            this.bookingFee = bookingFee;
        }
        this.refreshments = refreshments;
    }

    @Override
    public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {

        boolean booked = false;

        // Date is not more than 3 days ahead
        boolean dateValid = dateIsValid(required);

        if (dateValid) {
            if (super.book(firstName, lastName, required, numPassengers) == true) {;
                booked = true;
            }
        }

        return booked;

    }

    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        System.out.println(super.getDetails());
        sb.append(String.format("%-15s \n", "Refreshments"));
        for (int i = 0; i < refreshments.length; i++) {
            sb.append(String.format("%-15s %s\n", "Item " + (i + 1) + ":", refreshments[i]));
        }
        return sb.toString();
    }

    /*
     * Checks that the date is not more than 3 days in the future.
     */
    private boolean dateIsValid(DateTime date) {
        return DateUtilities.dateIsNotMoreThan3Days(date);
    }

    private boolean bookingFeeIsValid() {
        if (this.bookingFee >= 3) {
            return true;
        } else {
            return false;
        }
    }

}
