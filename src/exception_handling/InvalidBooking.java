package exception_handling;

/**
 *
 */
public class InvalidBooking extends RuntimeException {
    public InvalidBooking(String errorMessage){
        super(errorMessage);
    }
}
