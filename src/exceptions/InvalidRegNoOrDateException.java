package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidRegNoOrDateException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidRegNoOrDateException extends Exception {
	 
	public InvalidRegNoOrDateException(String message) {
		super(message);
	}
}
