package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidPassengerCapacityException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidPassengerCapacityException extends Exception {

	public InvalidPassengerCapacityException(String message) {
		super(message);
	}
}
