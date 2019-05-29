package exception_handling;

/**
 *
 */
public class InvalidRegID extends RuntimeException {
    public InvalidRegID(String errorMessage){
        super(errorMessage);
    }
}
