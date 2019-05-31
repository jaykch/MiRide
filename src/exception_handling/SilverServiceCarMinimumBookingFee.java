package exception_handling;

/*
 * Class:			SilverServiceCarMinimumBookingFee
 * Description:		Exception Class For Minimum Booking Fee for Silver Service Car 
 * Author:			Jay Kumar - S3770282
 */
public class SilverServiceCarMinimumBookingFee extends RuntimeException
{
	// String to store error message
	String errorMessage;

	public SilverServiceCarMinimumBookingFee(String errorMessage)
	{

		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}

}
