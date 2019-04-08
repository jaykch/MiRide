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
	public Booking book;

	// TODO: can current bookings and past bookings array be public

	// Car constructor
	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) {

		// Initialise variables for a car object

		this.regNo = regNo.substring(0, 6);
		this.make = make;
		this.model = model;
		this.driverName = driverName;

		// Make sure passenger capacity is always greater than 0 and less than 10

		if (passengerCapacity < 0) {
			this.passengerCapacity = 1;
		} else if (passengerCapacity > 10) {
			this.passengerCapacity = 10;
		} else {
			this.passengerCapacity = passengerCapacity;
		}

		// Check if car has 5 bookings

		if (this.currentBookings[4] == null) {
			this.available = true;
		} else {
			this.available = false;
		}
	}

	// --------METHODS------------//

	// Method for booking a car

	public boolean book(String firstName, String lastName, DateTime pickupDate, int numPassengers) {
		// TODO: add booking code
		book = new Booking(firstName, lastName, pickupDate, numPassengers, this);

		for (int i = 0; i < this.currentBookings.length; i++) {
			if (this.currentBookings[i] == null) {
				this.currentBookings[i] = book;
				return true;
			}
		}

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
	}

	// Method to return the details of a car

	public String getDetails() {

		// Note:Ctrl+Shift+f always reformats this
		String availability;
		if (available) {
			availability = "	Yes";
		} else {
			availability = "	No";
		}

		return "RegNo:		" + regNo + "\nMake & Model:	" + make + " " + model + "\nDriver Name:	" + driverName
				+ "\nCapacity:	" + passengerCapacity + "\nAvailabile: " + availability + "\n";
	}

	// Method to return the car details in the form of a string

	public String toString() {

		// TODO: what do they want to do in a toString method

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

	// Method to get driver name

	public String getDriverName() {
		return this.driverName;
	}

	// Method to get Passenger Capacity

	public int getPassengerCapacity() {
		return this.passengerCapacity;
	}
}