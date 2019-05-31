package exception_handling;

/*
 * Class:			InvalidBooking
 * Description:		Exception Class For Invalid Booking 
 * Author:			Jay Kumar - S3770282
 */

public class InvalidBooking extends RuntimeException
{

	// String to store error message
	String errorMessage;

	public InvalidBooking(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}

}
