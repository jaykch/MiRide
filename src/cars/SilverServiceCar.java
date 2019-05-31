package cars;

import exception_handling.InvalidRefreshments;
import exception_handling.SilverServiceCarMinimumBookingFee;
import utilities.DateTime;
import utilities.DateUtilities;

public class SilverServiceCar extends Car {

    // Silver service car attributes
    
    private String[] refreshments;

    // Constants
    private final double MINIMUM_BOOKING_FEE = 3.0;

    public SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
            double bookingFee, String[] refreshments) {
        super(regNo, make, model, driverName, passengerCapacity);
        if (refreshments.length < 3) {
            throw new InvalidRefreshments("List Of Refreshments Cannot Be Less Than 3");
        } else if (CheckForDuplicateItemsInRefreshments(refreshments) == true) {
            throw new InvalidRefreshments("List Of Refreshments Contains Duplicates.");
        }

        //If Booking Fee Is Less Than 3 Then Make it equal to the minimum booking fee 
        
        if (bookingFee < MINIMUM_BOOKING_FEE) {
            throw new SilverServiceCarMinimumBookingFee("Minimum Fee For SS Car Cannot Be Less Than 3. No Car Added");
        } else {
            super.setBookingFee(bookingFee);
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

    @Override
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getCarDetails());
        sb.append(getRefreshmentDetails());
        sb.append(super.getCurrentBookingsDetails());
        sb.append(super.getPastBookingsDetails());
        return sb.toString();
    }
    
    public String getRefreshmentDetails(){
        StringBuilder sb = new StringBuilder();
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
        if (super.getBookingFee() >= 3) {
            return true;
        } else {
            return false;
        }
    }

    private boolean CheckForDuplicateItemsInRefreshments(String[] refreshments) {
        boolean duplicates = false;
        for (int j = 0; j < refreshments.length; j++) {
            for (int k = j + 1; k < refreshments.length; k++) {
                if (k != j && refreshments[k].equalsIgnoreCase(refreshments[j])) {
                    duplicates = true;
                }
            }
        }
        return duplicates;
    }

    

    public String[] getRefreshments() {
        return refreshments;
    }


}
