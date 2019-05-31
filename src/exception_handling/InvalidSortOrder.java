package exception_handling;

/*
 * Class:			InvalidRegID
 * Description:		Exception Class For Invalid Sort Order 
 * Author:			Jay Kumar - S3770282
 */

public class InvalidSortOrder extends Exception
{
	// String to store error message
	String errorMessage;

	public InvalidSortOrder(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}
}
