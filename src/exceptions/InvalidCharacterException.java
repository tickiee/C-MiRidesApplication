package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidCharacterException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidCharacterException extends Exception {
	public InvalidCharacterException (String message) {
		super(message);
	}

}
