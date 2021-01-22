package exceptions;

/**
 * Description:	This class represents a custom exception. 
 * <br>
 * Class: InvalidServiceTypeException 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

@SuppressWarnings("serial")
public class InvalidServiceTypeException extends Exception{

	public InvalidServiceTypeException(String message) {
		super(message);
	}
}
