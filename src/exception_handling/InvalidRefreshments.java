package exception_handling;

/*
 * Class:			InvalidRefreshments
 * Description:		Exception Class For Invalid Refreshments 
 * Author:			Jay Kumar - S3770282
 */
public class InvalidRefreshments extends RuntimeException
{
	// String to store error message
	String errorMessage;

	public InvalidRefreshments(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override // and display error message
	public String getMessage()
	{
		return "Error: " + errorMessage;
	}
}
