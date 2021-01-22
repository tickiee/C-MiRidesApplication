package utilities;

import exceptions.InvalidPassengerCapacityException;
import exceptions.InvalidRegNoException;

/**
 * Description:	This class is created from Assignment 1 Solution. 
 * <br>
 * Exceptions were added.
 * <br>
 * Class: MiRidesUtilities
 */

public class MiRidesUtilities {
	private final static int ID_LENGTH = 6;
	
	public static String isRegNoValid(String regNo) throws Exception {
		int regNoLength = regNo.length();
		
		if(regNoLength != ID_LENGTH) {
			throw new InvalidRegNoException("Error: The registration number must be 6 characters");
//			return "Error: registration number must be 6 characters";
		}
		
		boolean letters = regNo.substring(0,3).matches("[a-zA-Z]+");
		
		if (!letters) {
			throw new InvalidRegNoException("Error: The registration number should begin with three alphabetical characters.");
//			return "Error: The registration number should begin with three alphabetical characters.";
		}
		
		boolean numbers = regNo.substring(3).matches("[0-9]+");
		
		if (!numbers) {
			throw new InvalidRegNoException("Error: The registration number should end with three numeric characters.");
//			return "Error: The registration number should end with three numeric characters.";
		}
		return regNo;
	}

	public static String isPassengerCapacityValid(int passengerCapacity) throws Exception {
		if(passengerCapacity > 0 && passengerCapacity < 10) {
			return "OK";
		}
		
		else {
			throw new InvalidPassengerCapacityException("Error: The number of passengers must be between 1 to 9");
//			return "Error: Passenger capacity must be between 1 and 9.";
		}
	}
}
