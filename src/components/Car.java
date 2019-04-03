package components;

import utils.DateTime;

public class Car {
	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;
	boolean availabile;
	private Booking book;

	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) {
		// TODO: how to make reg number alphabetical && numeric
		this.regNo = regNo.substring(0, 6);
		this.make = make;
		this.model = model;
		this.driverName = driverName;
		if (passengerCapacity < 0) {
			this.passengerCapacity = 1;
		} else if (passengerCapacity > 10) {
			this.passengerCapacity = 10;
		} else {
			this.passengerCapacity = passengerCapacity;
		}

		// Check if registration number contains first 3 alphabets and next 3 digits
		if (!regNo.substring(0, 3).matches("[a-zA-Z]+")) {
			// Check if it has anything other than alphabets
			System.out.println("First 3 charcters of a registration number should only be alphabets");
		}else if(!regNo.substring(3, 6).matches("[0-9]+")) {
			System.out.println("Last 3 charcters of a registration number should only be numbers");
		}

		// --------METHODS------------//
		// TODO: why does it mean by the method should not do the actual printing
	}

	public boolean book(String firstName, String lastName, int numPassengers) {
		// TODO: add booking code
		book = new Booking(firstName, lastName, new DateTime(), numPassengers, this);
		return true;
	}

	public String getDetails() {
		//Ctrl+Shift+f always reformats this 
		//TODO: try to use print f
		return "RegNo:		" + regNo + "\nMake & Model:	" + make + " " + model + "\nDriver Name:	" + driverName
				+ "\nCapacity:	" + passengerCapacity + "\nAvailability: ";
	}

	public String toString() {
		// TODO: what do they want to do in a toString method
		return regNo + ":" + make + ":" + model + ":" + driverName + ":" + passengerCapacity+ book.getBookingDate();
	}
}