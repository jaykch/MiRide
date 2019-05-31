package exception_handling;

/*
 * Class:			InvalidRegID
 * Description:		Exception Class For Invalid Registration ID 
 * Author:			Jay Kumar - S3770282
 */
public class InvalidRegID extends RuntimeException {

	// String to store error message
    String errorMessage;

	public InvalidRegID(String errorMessage)
	{
		this.errorMessage = errorMessage;
    }

	@Override // and display error message
    public String getMessage() {
        return "Error: " + errorMessage; 
    }
}
