package exception_handling;

/*
 * Class:			InvalidRegID
 * Description:		Exception Class For InvalidRegID 
 * Author:			Jay Kumar
 */

public class InvalidRegID extends RuntimeException {
    public InvalidRegID(String errorMessage){
        super(errorMessage);
    }
}
