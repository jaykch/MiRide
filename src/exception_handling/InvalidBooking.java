package exception_handling;

/*
 * Class:			InvalidBooking
 * Description:		Exception Class For Invalid Booking 
 * Author:			Jay Kumar
 */

public class InvalidBooking extends RuntimeException {
    String errorMessage;
    public InvalidBooking(String errorMessage) {
        this.errorMessage=errorMessage;
    }

    @Override
    public String getMessage() {
        return "Error: "+ errorMessage; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
