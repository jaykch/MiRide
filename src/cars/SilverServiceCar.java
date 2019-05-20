package cars;

import utilities.DateTime;
import utilities.DateUtilities;

public class SilverServiceCar extends Car
{

	// Silver service car attributes
	private double bookingFee;
	private String[] refreshments;

	// Constants
	private final double MINIMUM_BOOKING_FEE = 3.0;

	public SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments)
	{
		super(regNo, make, model, driverName, passengerCapacity);

		this.bookingFee = bookingFee;
		this.refreshments = refreshments;
	}

	public boolean book(String firstName, String lastName, DateTime required, int numPassengers)
	{

		boolean booked = false;

		// Date is not more than 3 days ahead
		boolean dateValid = dateIsValid(required);

		if (dateValid)
		{
			super.book(firstName, lastName, required, numPassengers);
			booked = true;
		}

		return booked;

	}

	public String getDetails()
	{
		return super.getDetails() + "test";
	}

	/*
	 * Checks that the date is not more than 3 days in the future.
	 */
	private boolean dateIsValid(DateTime date)
	{
		return DateUtilities.dateIsNotMoreThan3Days(date);
	}

}
