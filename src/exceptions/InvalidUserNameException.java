package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidUserNameException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidUserNameException extends Exception {
	
	public InvalidUserNameException(String message) {
		super(message);
	}

}
