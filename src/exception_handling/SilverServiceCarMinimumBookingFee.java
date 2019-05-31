package exception_handling;

/*
 * Class:			SilverServiceCarMinimumBookingFee
 * Description:		Exception Class For InvalidRegID 
 * Author:			Jay Kumar
 */
public class SilverServiceCarMinimumBookingFee extends RuntimeException {

    String errorMessage;

    public SilverServiceCarMinimumBookingFee(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return "Error: " + errorMessage; //To change body of generated methods, choose Tools | Templates.
    }

}
