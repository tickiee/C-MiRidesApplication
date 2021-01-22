package regex;

import exceptions.InvalidDateInputException;

/**
 * Description:	This class checks if the date entered by the user is valid.
 * <br>
 * Class: DateRegex
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class DateRegex {

	/**
	 * Description: These are the fields that describe the length of the date in string format.
	 */
	private int datelength = 10;
	
	/**
	 * Description: This method checks if the date is valid.
	 * @param date The date entered by the user.
	 * @throws InvalidDateInputException A custom exception thrown when the user inputs a incorrect date format.
	 */
	public DateRegex(String date) throws InvalidDateInputException {
		String userDate = date;
		
		// Checks if the length of the date is 10 since DD/MM/YYYY is 10 characters long in a string.
		if(userDate.length() != datelength) {
//			System.out.println("Error: Datelength");
			throw new InvalidDateInputException("Error: The date must be in DD/MM/YYYY.");
//			return "Error: registration number must be 6 characters";
		}
		
		else {
//			System.out.println(userDate.substring(0,2));
//			System.out.println("*");
//			System.out.println(userDate.substring(2,3));
//			System.out.println("*");
//			System.out.println(userDate.substring(3,5));
//			System.out.println("*");
//			System.out.println(userDate.substring(5,6));
//			System.out.println("*");
//			System.out.println(userDate.substring(6));
//			System.out.println("*");
			
			// Splits the string into substrings and checks if the date is valid.
			// For example, there is no 32th day, 13th month.
			// Only the year 2019 is allowed.
			boolean day = userDate.substring(0,2).matches("[0][1-9]") || userDate.substring(0,2).matches("[1][0-9]")
					 		|| userDate.substring(0,2).matches("[2][0-9]") || userDate.substring(0,2).matches("[3][0-1]");
			boolean month = userDate.substring(3,5).matches("[0][1-9]") || userDate.substring(3,5).matches("[1][0-2]");
			boolean year = userDate.substring(6).matches("2019");
			boolean slash = userDate.substring(2,3).matches("/") && userDate.substring(5,6).matches("/");
			
			// If any boolean value is false, throw exception
			if (!day) {
				throw new InvalidDateInputException("Error: The date must be in DD/MM/YYYY.");
//				System.out.println("Error: Day");
			}
			
			if (!month) {
				throw new InvalidDateInputException("Error: The date must be in DD/MM/YYYY.");
//				System.out.println("Error: Month");
			}
			
			if (!year) {
				throw new InvalidDateInputException("Error: The date must be in DD/MM/YYYY.");
//				System.out.println("Error : Year");
			}
			
			if (!slash) {
				throw new InvalidDateInputException("Error: The date must be in DD/MM/YYYY.");
//				System.out.println("Error: Slash");
			}
		}
	}
}
