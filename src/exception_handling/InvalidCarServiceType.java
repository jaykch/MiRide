package exception_handling;

/**
 *
 */
public class InvalidCarServiceType extends Exception{
    public InvalidCarServiceType(String errorMessage){
        super(errorMessage);
    }
}
