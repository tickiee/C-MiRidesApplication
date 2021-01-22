package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidBookingCarFullExcception 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidBookingCarFullExcception extends Exception {
	
	public InvalidBookingCarFullExcception(String message) {
		super(message);
	}
}
