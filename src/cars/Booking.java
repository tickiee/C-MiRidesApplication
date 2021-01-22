package cars;

import exceptions.InvalidUserNameException;
import utilities.DateTime;
import utilities.DateUtilities;

/*(Made by Mr Rodney Cocker)
 * Booking Class
 * Represents a booking in a ride sharing system.
 * This class can be used by other objects not just cars.
 * Author: Rodney Cocker
 */

/**
 * Description: This class represents a booking made by the user. 
 * <br>
 * 	This class is created from Assignment 1 Solution.
 * <br>
 * Class: Booking
 * @author Mr Rodney Cocker
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class Booking {
	
	/**
	 * Description: These are the fields that describe a booking.
	 */
	private String id;
	private String firstName;
	private String lastName;
	private DateTime dateBooked;
	private int numPassengers;
	private double bookingFee;
	private double kilometersTravelled;
	private double tripFee;
	
	private Car car;
	private String carId;
	
	private final int NAME_MINIMUM_LENGTH = 3;

	/**
	 * Description: The class Constructor. It creates a booking.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param required The date that the user booked the Car.
	 * @param numPassengers The number of passengers that the user booked the Car.
	 * @param car The Car that the user chose.
	 * @throws Exception The exceptions required.
	 */
	public Booking(String firstName, String lastName, DateTime required, int numPassengers, Car car) throws Exception {
		generateId(car.getRegistrationNumber(), firstName, lastName, required);
		validateAndSetDate(required);
		validateName(firstName, lastName);
		this.numPassengers = numPassengers;
		this.car = car;
		this.bookingFee = car.getTripFee();
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Updates the booking record with the kilometers traveled, the booking and trip fee.
	 */
	
	/**
	 * Description: This method updates the booking record with the kilometers traveled, the booking and trip fee.
	 * @param kilometersTravelled The distance travelled by the Car.
	 * @param tripFee The trip fee of the Car.
	 * @param bookingFee The booking fee of the Car.
	 */
	public void completeBooking(double kilometersTravelled, double tripFee, double bookingFee) {
		this.kilometersTravelled = kilometersTravelled;
		this.tripFee = tripFee;
		this.bookingFee = bookingFee;
	}
	
	/* (Made by Mr Rodney Cocker)
	 * A record marker mark the beginning of a record.
	 */
	
	/**
	 * Description: This method creates a record marker that marks the beginning of a record.
	 * @return A string that is a line that is used to mark the beginning of a record.
	 */
	private String getRecordMarker() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 40; i++) {
			sb.append("_");
		}

		sb.append("\n");
		
		return sb.toString();
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Generate an id from regNo, passenger name and the date of the booking.
	 */
	
	/**
	 * Description: This method generates an ID from the registration number of the Car, user's name and date of the booking.
	 * @param regNo The registration number of the Car.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param date The date that the user booked the Car.
	 */
	private void generateId(String regNo, String firstName, String lastName, DateTime date) {
		
		//Checks if the first and last name of the user is valid.
		if (firstName.length() < 3 || lastName.length() < 3  || date == null) {
			id = "Invalid";
		}
		
		else {
			id = regNo + firstName.substring(0, 3).toUpperCase() + lastName.substring(0, 3).toUpperCase()
				+ date.getEightDigitDate();
			carId = regNo;
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Ensures the name is more than three characters
	 */
	
	/**
	 * Description: This method checks if the names of the user is more than three characters long.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @throws Exception The exceptions required.
	 */
	private void validateName(String firstName, String lastName) throws Exception {
		
		//Checks if the first and last name of the user is valid.
		if (firstName.length() >= NAME_MINIMUM_LENGTH && lastName.length() >= NAME_MINIMUM_LENGTH) {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		else {
			firstName = "Invalid";
			lastName = "Invalid";
			throw new InvalidUserNameException("Error: First Name and Last Name must be 3 characters long");
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Ensures the date is not in the past.
	 */
	
	/**
	 * Description: This method checks if the date typed by the user is not in the past.
	 * @param date The date that the user booked the Car.
	 */
	private void validateAndSetDate(DateTime date) {
		if (DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan7Days(date)) {
			dateBooked = date;
		}
		
		else {
			dateBooked = null;
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Computer readable state of the car
	 */
	
	/**
	 * Description: This method is used by other methods to get the string of the booking details all in one line.
	 * @return A string representing the booking details all in one line, separated by a colon ':'.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(":" + bookingFee);
		
		// If there is a date, get the date.
		if (dateBooked != null) {
			sb.append(":" + dateBooked.getEightDigitDate());
		}
		
		else {
			sb.append(":" + "Invalid");
		}
		
		sb.append(":" + firstName + " " + lastName);
		sb.append(":" + numPassengers);
		sb.append(":" + kilometersTravelled);
		sb.append(":" + tripFee);
		sb.append(":" + car.getRegistrationNumber());
		
		return sb.toString();
	}

	
	/* (Made by Mr Rodney Cocker)
	 * Human readable presentation of the state of the car.
	 */
	
	/**
	 * Description: This method is used by other methods to get the details of the state of the Car.
	 * @return A string representing the details of the state of the Car.
	 */
	public String getDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-16s%-20s\n", " ", getRecordMarker()));
		sb.append(String.format("%-16s%-20s %s\n", " ", "id:", id));
		sb.append(String.format("%-16s%-20s $%.2f\n", " ", "Booking Fee:", bookingFee));
		
		// If there is a date, get the date.
		if(dateBooked != null) {
			sb.append(String.format("%-16s%-20s %s\n", " ", "Pick Up Date:", dateBooked.getFormattedDate()));
		}
		
		else {
			sb.append(String.format("%-16s%-20s %s\n", " ", "Pick Up Date:", "Invalid"));
		}
		
		sb.append(String.format("%-16s%-20s %s\n", " ", "Name:", firstName + " " + lastName));
		sb.append(String.format("%-16s%-20s %s\n", " ", "Passengers:", numPassengers));
		
		// If there is distance recorded, which also means if the booking is completed.
		if (kilometersTravelled == 0) {
			sb.append(String.format("%-16s%-20s %s\n", " ", "Travelled:", "N/A"));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Trip Fee:", "N/A"));
		}
		
		else {
			sb.append(String.format("%-16s%-20s %.2f\n", " ", "Travelled:", kilometersTravelled));
			sb.append(String.format("%-16s%-20s %.2f\n", " ", "Trip Fee:", tripFee));
		}
		
		sb.append(String.format("%-16s%-20s %s\n", " ", "Car Id:", car.getRegistrationNumber()));
		
		return sb.toString();
	}
	
	// Required getters
	
	/**
	 * Description: This method returns the first name of the user.
	 * @return A string representing the first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Description: This method returns the last name of the user.
	 * @return A string representing the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Description: This method returns the booking date of the booking.
	 * @return A date representing the date of the booking.
	 */
	public DateTime getBookingDate() {
		return dateBooked;
	}
	
	/**
	 * Description: This method returns the ID created by the method generateId. 
	 * @return A string representing an ID.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Description: This method returns the number of passengers that the user requires.
	 * @return An integer representing the number of passengers.
	 */
	public int getNumPassengers() {
		return numPassengers;
	}
	
	/**
	 * Description: This method returns the booking fee.
	 * @return A double representing the booking fee.
	 */
	public double getBookingFee() {
		return bookingFee;
	}
	
	/**
	 * Description: This method returns the registration number of the car.
	 * @return A string of the registration number of the car.
	 */
	public String getCarID() {
		return carId;
	}
}
