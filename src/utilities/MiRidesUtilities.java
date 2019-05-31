package utilities;

import exception_handling.InvalidRegID;

/*
 * Class:			DateTime
 * Description:		Helful Utilities for the application itself 
 * Author:			Rodney Cocker
 */

public class MiRidesUtilities {

    private final static int ID_LENGTH = 6;

    public static String isRegNoValid(String regNo) {
        int regNoLength = regNo.length();
        if (regNoLength != ID_LENGTH) {
            throw new InvalidRegID("Registration Number must be 6 characters");
            
        }
        boolean letters = regNo.substring(0, 3).matches("[a-zA-Z]+");
        if (!letters) {
            throw new InvalidRegID("The registration number should begin with three alphabetical characters.");
            
        }
        boolean numbers = regNo.substring(3).matches("[0-9]+");
        if (!numbers) {
            throw new InvalidRegID("The registration number should end with three numeric characters.");
            
        }
        return regNo;
    }

}
