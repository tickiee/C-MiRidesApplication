package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidDriverNameException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */
@SuppressWarnings("serial")
public class InvalidDriverNameException extends Exception {
	
	public InvalidDriverNameException(String message) {
		super(message);
	}
}
