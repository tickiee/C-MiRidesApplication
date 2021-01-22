package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidMakeException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidMakeException extends Exception {
	
	public InvalidMakeException(String message) {
		super(message);
	}

}
