package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidSortingOrderException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidSortingOrderException extends Exception {
	
	public InvalidSortingOrderException(String message) {
		super(message);
	}

}
