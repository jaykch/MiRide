package exception_handling;

/*
 * Class:			InvalidDate
 * Description:		Exception Class For Invalid Date 
 * Author:			Jay Kumar - S3770282
 */

public class InvalidDate extends Exception
{
	// String to store error message
	String errorMessage;

	public InvalidDate(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}
}
