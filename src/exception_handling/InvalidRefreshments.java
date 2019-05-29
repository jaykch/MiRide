package exception_handling;

/**
 *
 */
public class InvalidRefreshments extends RuntimeException {
    public InvalidRefreshments(String errorMessage){
        super(errorMessage);
    }
}
