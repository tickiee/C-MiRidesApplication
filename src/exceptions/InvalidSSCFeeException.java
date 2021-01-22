package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidSSCFeeException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidSSCFeeException extends Exception {
	
	public InvalidSSCFeeException(String message) {
		super(message);
	}
}
