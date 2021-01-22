package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidBookingDateException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidBookingDateException extends Exception {

	public InvalidBookingDateException(String message) {
		super(message);
	}
}
