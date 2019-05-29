package exception_handling;

/**
 *
 */
public class InvalidDate extends Exception {
    public InvalidDate(String errorMessage){
        super(errorMessage);
    }
}
