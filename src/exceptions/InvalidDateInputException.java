package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidDateInputException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidDateInputException extends Exception {

	public InvalidDateInputException(String message) {
		super(message);
	}
}
