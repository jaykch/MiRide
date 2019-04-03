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

	public Booking(String firstName, String lastName, DateTime pickupDate, int numPassengers, Car car) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pickupDate = pickupDate;
		this.numPassengers = numPassengers;
		this.car = car;
	}

	public String getBookingDate() {
		return pickupDate.getFormattedDate();
	}
}
