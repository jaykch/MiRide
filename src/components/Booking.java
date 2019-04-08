package components;

import utils.DateTime;

//TODO: add business rules

public class Booking {
	private String id;
	private double bookingFee;
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

	// Method to complete booking

	public void completeBooking(double kilometersTravelled) {
		this.kilometersTravelled = kilometersTravelled;
		this.tripFee = kilometersTravelled * 30 * 1.5 / 100;
		this.isBookingCompleted = true;
		car.moveCompletedBooking();
	}
}