package main;

import app.Menu;

/**
 * Description:	This class represents the driver that is used to run the whole application. 
 * <br>
 * This class is created from Assignment 1 Solution.
 * <br>
 * Class: Driver
 * @author Mr Rodney Cocker
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class Driver {

	public static void main(String[] args) throws Exception {
		Menu menu = new Menu();
		menu.run();
	}
}
