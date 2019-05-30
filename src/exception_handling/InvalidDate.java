package exception_handling;

/*
 * Class:			InvalidDate
 * Description:		Exception Class For InvalidDate 
 * Author:			Jay Kumar
 */

public class InvalidDate extends Exception {
    public InvalidDate(String errorMessage){
        super(errorMessage);
    }
}
