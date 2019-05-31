package exception_handling;

/*
 * Class:			InvalidCarServiceType
 * Description:		Exception Class For InvalidCarServiceType 
 * Author:			Jay Kumar
 */

public class InvalidCarServiceType extends Exception{
    String errorMessage;
    
    public InvalidCarServiceType(String errorMessage){
        this.errorMessage=errorMessage;
    }
    
    @Override
    public String getMessage() {
        return "Error: " + errorMessage; //To change body of generated methods, choose Tools | Templates.
    }
}
