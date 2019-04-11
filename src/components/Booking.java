/*
* Class: 		Bookings
* Description: 	The class represents a single booking record for
* 				any object that can be booked.
* Author: 		Jay Kumar - S3770282
*/

package components;

import utils.DateTime;

public class Booking {
	private String id;
	private double bookingFee = 1.50;
	private DateTime pickupDate;
	private String firstName;
	private String lastName;
	private int numPassengers;
	private double kilometersTravelled;
	private double tripFee;
	private Car car;

	// Extra variable to complete a booking
	public boolean isBookingCompleted;

	// Booking constructor

	public Booking(String firstName, String lastName, DateTime pickupDate, int numPassengers, Car car) {

		// Initialise variables for a booking object

		this.firstName = firstName;
		this.lastName = lastName;
		this.pickupDate = pickupDate;
		this.numPassengers = numPassengers;
		this.car = car;
		this.id = this.car.getRegNo() + "_" + this.firstName.substring(0, 3).toUpperCase()
				+ this.lastName.substring(0, 3).toUpperCase() + "_" + this.pickupDate.getEightDigitDate();
	}

	// --------METHODS------------//

	// Method to return the booking pick up date

	public String getBookingDate() {
		return pickupDate.getFormattedDate();
	}

	// Method to return the details of a booking

	public String getDetails() {
		
		/*
		* ALGORITHM
		* BEGIN
		* 	GET details of a booking
		* 		COMPUTE booking completed or not
		* 			IF booking is completed
		* 				COMPUTE kilometres travelled
		* 				COMPUTE trip fee
		* 			ELSE
		* 				SET kilometres travelled to N/A
		* 				SET trip fee to N/A
		* 	DISPLAY details
		* END
		*/

		if (isBookingCompleted) {
			return "id:		" + id + "\nBooking Fee:	" + bookingFee + "\nPick up Date:	"
					+ pickupDate.getFormattedDate() + "\nName:		" + firstName + " " + lastName + "\nPassengers:	"
					+ numPassengers + "\nTravelled:	" + kilometersTravelled + "\nTrip Fee: 	" + tripFee
					+ "\nCar id: 	" + car.getRegNo() + "\n";
		} else {
			return "id:		" + id + "\nBooking Fee:	" + bookingFee + "\nPick up Date:	"
					+ pickupDate.getFormattedDate() + "\nName:		" + firstName + " " + lastName + "\nPassengers:	"
					+ numPassengers + "\nTravelled:	" + "N/A" + "\nTrip Fee: 	" + "N/A" + "\nCar id: 	"
					+ car.getRegNo() + "\n";
		}

	}

	// Method to return the booking details in the form of a string
	public String toString() {

		return id + ":" + bookingFee + ":" + pickupDate + ":" + firstName + " " + lastName + ":" + kilometersTravelled
				+ ":" + tripFee + ":" + car.getRegNo() + "\n";
	}

	// Method to get booking id

	public String getBookingID() {
		return this.id;
	}

	// Method to get first name

	public String getFirstName() {
		return this.firstName;
	}

	// Method to get last name

	public String getLastName() {
		return this.lastName;
	}
	
	// Method to get booking fee
	
	public Double getBookingFee() {
		return this.bookingFee;
	}

	// Method to complete booking

	public Double completeBooking(double kilometersTravelled) {
				
		this.kilometersTravelled = kilometersTravelled;
		this.tripFee = kilometersTravelled * 30 * this.bookingFee / 100;
		this.isBookingCompleted = true;
		car.moveCompletedBooking();
				
		return this.tripFee;
	}
}
