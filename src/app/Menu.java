package app;

import java.io.IOException;
import java.util.Scanner;
import exceptions.CorruptedFileException;
import exceptions.InvalidBookingCarFullExcception;
import exceptions.InvalidBookingDateException;
import exceptions.InvalidCharacterException;
import exceptions.InvalidDateInputException;
import exceptions.InvalidDriverNameException;
import exceptions.InvalidExceedPassengerCapacity;
import exceptions.InvalidMakeException;
import exceptions.InvalidModelException;
import exceptions.InvalidPassengerCapacityException;
import exceptions.InvalidRefreshmentsException;
import exceptions.InvalidRegNoException;
import exceptions.InvalidRegNoOrDateException;
import exceptions.InvalidSSCFeeException;
import exceptions.InvalidServiceTypeException;
import exceptions.InvalidSortingOrderException;
import exceptions.InvalidUserNameException;
import exceptions.InvalidZeroUserInputException;
import regex.DateRegex;
import utilities.DateTime;
import utilities.DateUtilities;

/*
 * Class:		Menu
 * Description:	The class a menu and is used to interact with the user. 
 * Author:		Rodney Cocker
 */

/**
 * Description:	This class is the main menu of the application.
 * <br>
 * This class is created from Assignment 1 Solution.
 * <br>
 * Class: Menu
 * @author Mr Rodney Cocker
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */
public class Menu {
	
	private Scanner console = new Scanner(System.in);
	private MiRideApplication application = new MiRideApplication();
	
	// Allows me to turn validation on/off for testing business logic in the classes.
	private boolean testingWithValidation = true;

	/* (Made by Mr Rodney Cocker)
	 * Prints the menu.
	 */
	
	/**
	 * Description: This method prints the start menu and is the main menu for the whole application.
	 */
	private void printMenu() {
		System.out.printf("\n********** MiRide System Menu **********\n\n");

		System.out.printf("%-30s %s\n", "Create Car", "CC");
		System.out.printf("%-30s %s\n", "Book Car", "BC");
		System.out.printf("%-30s %s\n", "Complete Booking", "CB");
		System.out.printf("%-30s %s\n", "Display ALL Cars", "DA");
		System.out.printf("%-30s %s\n", "Search Specific Car", "SS");
		System.out.printf("%-30s %s\n", "Search Available Cars", "SA");
		System.out.printf("%-30s %s\n", "Seed Data", "SD");
		System.out.printf("%-30s %s\n", "Load Cars", "LC");
		System.out.printf("%-30s %s\n", "Exit Program", "EX");
		System.out.println("\nEnter your selection: ");
		System.out.println("(Hit enter to cancel any operation)");
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Runs the menu in a loop until the user decides to exit the system.
	 */
	
	/**
	 * Description: This method runs the menu in a loop until the user decides to exit the system.
	 * @throws Exception The exceptions required.
	 */
	public void run() throws Exception {
		
		final int MENU_ITEM_LENGTH = 2;
		String input;
		String choice = "";
		
		// Runs the Menu page until the user exits.
		do {
			printMenu();
			input = console.nextLine().toUpperCase();
			
			if (input.length() != MENU_ITEM_LENGTH) {
				System.out.println("Error - selection must be two characters!");
			} 
			
			else {
				System.out.println();
				
				//Catches the user's choice and follows up from there.
				switch (input) {
				case "CC":
					try {
						createCar();
					}
					
					catch (InvalidRegNoException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidMakeException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidModelException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidDriverNameException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidPassengerCapacityException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidServiceTypeException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidRefreshmentsException e) {
						System.out.println(e.getMessage());
					}
					
					catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
					
					catch  (InvalidSSCFeeException e) {
						System.out.println(e.getMessage());
					}
					
					break;
				case "BC":
					try {
						book();
					}
					
					catch (InvalidDateInputException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidUserNameException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidBookingDateException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidBookingCarFullExcception e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidExceedPassengerCapacity e) {
						System.out.println(e.getMessage());
					}
					
					catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidZeroUserInputException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidCharacterException e) {
						System.out.println(e.getMessage());
					}
					
					break;
				case "CB":
					try {
						completeBooking();
					}
					
					catch (InvalidRegNoOrDateException e) {
						System.out.println(e.getMessage());
					}
					
					break;
				case "DA":
					try {
						dispAllCar();
					}
					
					catch (InvalidSortingOrderException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidServiceTypeException e) {
						System.out.println(e.getMessage());
					}
					
					break;
				case "SS":
					searchSpecificCar();
					break;
				case "SA":
					try {
						searchAvailableCar();
					}
					
					catch (InvalidServiceTypeException e) {
						System.out.println(e.getMessage());
					}
					
					catch (InvalidDateInputException e) {
						System.out.println(e.getMessage());
					}
					
					break;
				case "SD":
					application.seedData();
					break;
				case "LC":
					//Currently, there is only 1 Standard and 1 Silver Service Car in the text file.
					try {
						application.loadCars();
					} 
					
					catch (IOException e) {
						System.out.println("Data could not be retrieved, please contact the adminstrator.");
					} 
					
					catch (CorruptedFileException e) {
//						System.out.println("Data was corrupt, please contact the adminstrator.");
						System.out.println(e.getMessage());
					}
					
					break;
				case "EX":
					try {
						application.saveCars();
						System.out.println("Cars saved");
						choice = "EX";
						System.out.println("Exiting Program ... Goodbye!");
					}
					
					catch (IOException e) {
						System.out.println("Data could not be saved, please contact the adminstrator.");
					}
					
					break;
				default:
					System.out.println("Error, invalid option selected!");
					System.out.println("Please try Again...");
				}
			}
		} while (choice != "EX");
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Creates cars for use in the system available or booking.
	 */
	
	/**
	 * Description: This method creates a car.
	 * @throws Exception The exceptions required.
	 */
	private void createCar() throws Exception {
		String id = "", make, model, driverName;
		String type, refreshments;
		boolean proceed = true;
		double sscStandardFee = 0;
		int numPassengers = 0;
		System.out.print("Enter registration number: ");
		id = promptUserForRegNo();
		
		if (id.length() != 0) {
			
			// Get details required for creating a car.
			System.out.print("Enter Make: ");
			make = console.nextLine();
			
			if (make.length() == 0) {
				throw new InvalidMakeException("Error: Make is empty.");
			}

			System.out.print("Enter Model: ");
			model = console.nextLine();
			
			if (model.length() == 0) {
				throw new InvalidModelException("Error: Model is empty.");
			}

			System.out.print("Enter Driver Name: ");
			driverName = console.nextLine();
			
			if (driverName.length() == 0) {
				throw new InvalidDriverNameException("Error: Driver Name is empty.");
			}
			
			try {
				System.out.print("Enter number of passengers: ");
				numPassengers = promptForPassengerNumbers();
			}
			
			catch (NumberFormatException e) {
				System.out.println("Error: Number should be an integer from 1 to 9");
				proceed = false;
			}
			
			// If there is no errors, continue.
			if (proceed) {
				System.out.println("Enter Service Type (SD/SS): ");
				
				type = console.nextLine().toUpperCase();
				
				if (!type.equals("SD") && !type.equals("SS")) {
					throw new InvalidServiceTypeException("Error: Service Type is invalid.");
				}
				
				if (type.equals("SD")) {
					boolean result = application.checkIfCarExists(id);
		
					if (!result) {
						String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers);
						System.out.println(carRegistrationNumber);
					} 
					
					else {
						throw new InvalidRegNoException("Error: Already exists in the system");
//						System.out.println("Error - Already exists in the system");
					}
				}
				
				else if (type.equals("SS")) {
					boolean proceed2 = true;
					
					try {
						System.out.println("Enter Standard Fee: ");
						sscStandardFee = Double.parseDouble(console.nextLine());
					}
					
					catch (NumberFormatException e) {
						System.out.println("Error: Please input a numeric value");
						proceed2 = false;
					}
					
					if (proceed2) {
						System.out.println("Enter List of Refreshments: ");
						refreshments = console.nextLine();
						
						if (refreshments.substring(refreshments.length() - 1).equals(",")) {
							refreshments = refreshments.substring(0, refreshments.length() - 1);
						}
						
						boolean result = application.checkIfCarExists(id);
						boolean result2 = application.bookingFeeSilverServiceCar(sscStandardFee);
						
						if (!result && result2) {
							String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers, 
																				sscStandardFee, refreshments);
							System.out.println(carRegistrationNumber);
						} 
						
						else if (!result && !result2) {
							throw new InvalidSSCFeeException("Error - Standard Fee must be $3.00 or more.");
	//						System.out.println("Error - Standard Fee must be $3.00 or more.");
						}
						
						else {
							throw new InvalidRegNoException("Error - Already exists in the system");
	//						System.out.println("Error - Already exists in the system");
						}
					}
				}
			}
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Prompt user for registration number and validate it is in the correct form.
	 * Boolean value for indicating test mode allows by passing validation to test
	 * program without user input validation.
	 */
	
	/**
	 * Description: This method prompts and checks if the registration number of the car is valid.
	 * @return A boolean value representing if the registration number is valid.
	 * @throws Exception The exceptions required.
	 */
	private String promptUserForRegNo() throws Exception {
		String regNo = "";
		boolean validRegistrationNumber = false;
		
		// By pass user input validation.
		if (!testingWithValidation) {
			return console.nextLine();
		} 
		
		else {
			
			while (!validRegistrationNumber) {
				regNo = console.nextLine();
				boolean exists = application.checkIfCarExists(regNo);
				
				if (exists) {
					// Empty string means the menu will not try to process
					// the registration number
					System.out.println("Error: Reg Number already exists");
					return "";
				}
				
				if (regNo.length() == 0) {
					throw new InvalidRegNoException("Error: Registration number is empty.");
				}

				String validId = application.isValidId(regNo);
				
				if (validId.contains("Error:")) {
					System.out.println(validId);
					System.out.println("Enter registration number: ");
					System.out.println("(or hit ENTER to exit)");
				} 
				
				else {
					validRegistrationNumber = true;
				}
			}
			
			return regNo;
		}
	}
	
	/**
	 * Description: This method prompts and checks if the number of seats in a car is valid.
	 * @return An integer representing the number of seats in a car if it is valid.
	 * @throws Exception The exceptions required.
	 */
	private int promptForPassengerNumbers() throws Exception {
		int numPassengers = 0;
		boolean validPassengerNumbers = false;
		
		// By pass user input validation.
		if (!testingWithValidation) {
			return Integer.parseInt(console.nextLine());
		} 
		
		else {
			
			while (!validPassengerNumbers) {
				numPassengers = Integer.parseInt(console.nextLine());
				String validId = application.isValidPassengerCapacity(numPassengers);
				
				if (validId.contains("Error:")) {
					System.out.println(validId);
					System.out.println("Enter passenger capacity: ");
					System.out.println("(or hit ENTER to exit)");
				} 
				
				else {
					validPassengerNumbers = true;
				}
			}
			
			return numPassengers;
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Book a car by finding available cars for a specified date.
	 */
	
	/**
	 * Description: This method books a car by finding available cars for a specific date.
	 * @return A boolean value that checks if the booking is successful.
	 * @throws Exception The exceptions required.
	 */
	private boolean book() throws Exception {
		System.out.println("Enter date car required: ");
		System.out.println("format DD/MM/YYYY)");
		String dateEntered = console.nextLine();
		DateRegex dateChecker = new DateRegex(dateEntered);
		int day = Integer.parseInt(dateEntered.substring(0, 2));
		int month = Integer.parseInt(dateEntered.substring(3, 5));
		int year = Integer.parseInt(dateEntered.substring(6));
		DateTime dateRequired = new DateTime(day, month, year);
		
		if (!DateUtilities.dateIsNotInPast(dateRequired) || !DateUtilities.dateIsNotMoreThan7Days(dateRequired)) {
			throw new InvalidBookingDateException("Error: Date is invalid, must be within the coming week.");
//			System.out.println("Date is invalid, must be within the coming week.");
//			return false;
		}
		
		String[] availableCars = application.book(dateRequired);
		
		for (int i = 0; i < availableCars.length; i++) {
			System.out.println(availableCars[i]);
		}
		
		if (availableCars.length != 0) {
			System.out.println("Please enter a number from the list:");
			String itemSelectedInString = console.nextLine();
			
			try {
				int numberStringChecker = Integer.parseInt(itemSelectedInString);
			}
			
			catch (NumberFormatException e) {
				System.out.println("Error: Please enter a numeric value.");
			}
			
//			if (itemSelectedInString.contains("[a-zA-Z]+")) {
//				throw new InvalidCharacterException("Error: Input must be a numeric value.");
//			}
			
			int itemSelected = Integer.parseInt(itemSelectedInString);
			
			if (itemSelected == 0) {
				throw new InvalidZeroUserInputException("Error: Number cannot be 0.");
			}
			
			String regNo = availableCars[itemSelected - 1];
			regNo = regNo.substring(regNo.length() - 6);
			System.out.println("Please enter your first name:");
			String firstName = console.nextLine();
			System.out.println("Please enter your last name:");
			String lastName = console.nextLine();
			System.out.println("Please enter the number of passengers:");
			
			try {
				int numPassengers = Integer.parseInt(console.nextLine());
				String result = application.book(firstName, lastName, dateRequired, numPassengers, regNo);
				System.out.println(result);
			}
			
			catch (NumberFormatException e) {
				System.out.println("Error: The number of passengers should be in numeric input.");
			}
		} 
		
		else {
			System.out.println("There are no available cars on this date.");
		}
		
		return true;
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Complete bookings found by either registration number or booking date.
	 */
	
	/**
	 * Description: This method complete bookings that are found by either the registration number or the booking date.
	 * @throws Exception The exceptions required.
	 */
	private void completeBooking() throws Exception {
		int regNoLength = 6;
		int dateLength = 10;
		System.out.print("Enter Registration or Booking Date:");
		String response = console.nextLine();
		
		if (response.length() != regNoLength) {
			
			if (response.length() != dateLength) {
				throw new InvalidRegNoOrDateException("Error: Invalid character length");
			}
		}
		
		String result;
		
		// User entered a booking date
		if (response.contains("/")) {
			String dateEntered = response;
			DateRegex dateChecker = new DateRegex(dateEntered);
			System.out.print("Enter First Name:");
			String firstName = console.nextLine();
			System.out.print("Enter Last Name:");
			String lastName = console.nextLine();
			
			try {
				System.out.print("Enter kilometers:");
				double kilometers = Double.parseDouble(console.nextLine());
				int day = Integer.parseInt(response.substring(0, 2));
				int month = Integer.parseInt(response.substring(3, 5));
				int year = Integer.parseInt(response.substring(6));
				DateTime dateOfBooking = new DateTime(day, month, year);
				result = application.completeBooking(firstName, lastName, dateOfBooking, kilometers);
				System.out.println(result);
			}
			
			catch (NumberFormatException e){
				System.out.println("Error: The number of passengers should be in numeric input.");
			}
			
		} 
		
		else {
			System.out.print("Enter First Name:");
			String firstName = console.nextLine();
			System.out.print("Enter Last Name:");
			String lastName = console.nextLine();
			
			if (application.getBookingByName(firstName, lastName, response)) {
				
				try {
					System.out.print("Enter kilometers:");
					double kilometers = Double.parseDouble(console.nextLine());
					result = application.completeBooking(firstName, lastName, response, kilometers);
					System.out.println(result);
				}
				
				catch (NumberFormatException e){
					System.out.println("Error: The number of passengers should be in numeric input.");
				}
			}
			
			else {
				System.out.println("Error: Booking not found.");
			}
		}
	}

	/**
	 * Description: This method displays all the cars currently in the application.
	 * @throws Exception The exceptions required.
	 */
	private void dispAllCar() throws Exception {
		String type, sortOrder;
		System.out.println("Enter Type (SD/SS): ");
		type = console.nextLine().toUpperCase();
		
		if (!type.equals("SD") && !type.equals("SS")) {
			throw new InvalidServiceTypeException("Error: Service Type is invalid.");
		}
		
		System.out.println("Enter Sorting Order (A/D): ");
		sortOrder = console.nextLine().toUpperCase();
		
		if (!sortOrder.equals("A") && !sortOrder.equals("D")) {
			throw new InvalidSortingOrderException("Error: Sorting Order is invalid.");
		}
		
		System.out.println(application.displayAllBookings(type, sortOrder));
	}
	
	/**
	 * Description: This method prompts the user to enter the registration number to search a specific car.
	 */
	private void searchSpecificCar() {
		System.out.print("Enter Registration Number: ");
		System.out.println(application.displaySpecificCar(console.nextLine()));
	}
	
	/**
	 * Description: This method searches the cars that are available on the day entered by the user.
	 * @throws Exception The exceptions required.
	 */
	private void searchAvailableCar() throws Exception {	
		String type;
		System.out.print("Enter Type (SD/SS): ");
		type = console.nextLine().toUpperCase();
		
		if (!type.equals("SD") && !type.equals("SS")) {
			throw new InvalidServiceTypeException("Error: Service Type is invalid.");
		}
		
		System.out.println("Enter Date: ");
		String dateEntered = console.nextLine();
		DateRegex dateChecker = new DateRegex(dateEntered);
		int day = Integer.parseInt(dateEntered.substring(0, 2));
		int month = Integer.parseInt(dateEntered.substring(3, 5));
		int year = Integer.parseInt(dateEntered.substring(6));
		DateTime dateRequired = new DateTime(day, month, year);
		application.book(dateRequired, type);
//		for (int i = 0; i < availableCars.length; i++) {
//			System.out.println(availableCars[i]);
//		}
	}
}
