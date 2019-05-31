package exception_handling;

/*
 * Class:			InvalidCarServiceType
 * Description:		Exception Class For Invalid Car Service Type 
 * Author:			Jay Kumar - S3770282
 */

public class InvalidCarServiceType extends Exception
{
	// String to store error message
	String errorMessage;

	public InvalidCarServiceType(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}
}
