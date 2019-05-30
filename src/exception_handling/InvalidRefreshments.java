package exception_handling;

/*
 * Class:			InvalidRefreshments
 * Description:		Exception Class For InvalidRefreshments 
 * Author:			Jay Kumar
 */

public class InvalidRefreshments extends RuntimeException {
    public InvalidRefreshments(String errorMessage){
        super(errorMessage);
    }
}
