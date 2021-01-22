package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidRegNoException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidRegNoException extends Exception {
	
	public InvalidRegNoException(String message) {
		super(message);
	}
}