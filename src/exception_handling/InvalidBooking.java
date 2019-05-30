package exception_handling;

/*
 * Class:			InvalidBooking
 * Description:		Exception Class For InvalidBooking 
 * Author:			Jay Kumar
 */

public class InvalidBooking extends RuntimeException {

    public InvalidBooking(String errorMessage) {
        super(errorMessage);
    }
}
