package exception_handling;

/*
 * Class:			InvalidRegID
 * Description:		Exception Class For InvalidRegID 
 * Author:			Jay Kumar
 */
public class InvalidRegID extends RuntimeException {

    String errorMessage;

    public InvalidRegID(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return "Error: " + errorMessage; //To change body of generated methods, choose Tools | Templates.
    }
}
