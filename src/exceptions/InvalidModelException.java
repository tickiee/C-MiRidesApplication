package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidModelException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidModelException extends Exception {

	public InvalidModelException(String message) {
		super(message);
	}
}
