/*
* Class: 		Car
* Description: 	The class represents a single record for any car 
* 				that can be created.
* Author: 		Jay Kumar - S3770282
*/

package components;

import utils.DateTime;

public class Car {
	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;
	private boolean available;
	private Booking[] currentBookings = new Booking[5];
	private Booking[] pastBookings = new Booking[100];
	
	// Using public booking method to be able to book easily for seed data
	public Booking book;

	// By default car is available on a date and every time a pickup date is
	// entered it will check if the car is booked on that date
	private boolean availableOnDate = true;

	// Car constructor
	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) {

		// Initialise variables for a car object

		this.regNo = regNo.substring(0, 6);
		this.make = make;
		this.model = model;
		this.driverName = driverName;

		// Make sure passenger capacity is always greater than 0 and less than 10

		if (passengerCapacity <= 0) {
			this.passengerCapacity = 1;
		} else if (passengerCapacity > 10) {
			this.passengerCapacity = 10;
		} else {
			this.passengerCapacity = passengerCapacity;
		}

		// Check if car is available
		this.checkAvailability();
	}

	// --------METHODS------------//

	// Method for booking a car

	public boolean book(String firstName, String lastName, DateTime pickupDate, int numPassengers) {

		// Create a new booking

		book = new Booking(firstName, lastName, pickupDate, numPassengers, this);

		// Add booking to current bookings

		for (int i = 0; i < this.currentBookings.length; i++) {
			if (this.currentBookings[i] == null) {
				this.currentBookings[i] = book;
				return true;
			}
		}

		// If no slot available for booking check availability and set it to false and
		// return false for the method

		this.checkAvailability();

		System.out.println("completely booked!");
		return false;
	}

	// Method to move completed bookings into past bookings array
	public void moveCompletedBooking() {
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i].isBookingCompleted) {
				for (int j = 0; j < pastBookings.length; j++) {
					if (pastBookings[j] == null) {
						pastBookings[j] = currentBookings[i];
						break;
					}
				}
				currentBookings[i] = null;
				break;
			}
		}
		this.checkAvailability();
	}

	// Method to return the details of a car

	public String getDetails() {
		
		/*
		* ALGORITHM
		* BEGIN
		* 	GET details of a car
		* 	COMPUTE car available or not
		* 		IF car is available for booking
		* 			SET availability to yes
		* 		ELSE
		* 			SET availability to no
		* 	DISPLAY details
		* END
		*/

		// Set variable to print yes or no to the availability field

		this.checkAvailability();

		String availability;
		if (available) {
			availability = "	Yes";
		} else {
			availability = "	No";
		}

		// Note:Ctrl+Shift+f always reformats this

		return "RegNo:		" + regNo + "\nMake & Model:	" + make + " " + model + "\nDriver Name:	" + driverName
				+ "\nCapacity:	" + passengerCapacity + "\nAvailabile: " + availability + "\n";
	}

	// Method to return the car details in the form of a string

	public String toString() {
		return regNo + ":" + make + ":" + model + ":" + driverName + ":" + passengerCapacity + book.getBookingDate();
	}

	// Method to return car registration number

	public String getRegNo() {
		return regNo;
	}

	// Method to get current bookings

	public Booking[] getCurrentBookings() {
		return this.currentBookings;
	}

	// Method to get past bookings

	public Booking[] getPastBookings() {
		return this.pastBookings;
	}

	// Method to get availability
	public Boolean isAvailable() {
		this.checkAvailability();
		return this.available;
	}

	// Method to set availability
	public void checkAvailability() {

		// Set availability to false unless proven later
		this.available = false;

		// Check if car has an empty booking slot

		for (int i = 0; i < currentBookings.length; i++) {
			if (this.currentBookings[i] == null) {
				this.available = true;
				break;
			}
		}

	}

	// Method to get driver name

	public String getDriverName() {
		return this.driverName;
	}

	// Method to get Passenger Capacity

	public int getPassengerCapacity() {
		return this.passengerCapacity;
	}

	// Method to get if its available on a date
	
	public boolean isAvailableOnDate() {
		return availableOnDate;
	}
	
	// Method to set its availability on a date

	public void setAvailableOnDate(boolean availableOnDate) {
		this.availableOnDate = availableOnDate;
	}
}