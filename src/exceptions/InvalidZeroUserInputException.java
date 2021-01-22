package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidZeroUserInputException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidZeroUserInputException extends Exception{

	public InvalidZeroUserInputException(String message) {
		super(message);
	}
}
