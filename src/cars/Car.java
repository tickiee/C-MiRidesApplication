package cars;

import exceptions.InvalidBookingCarFullExcception;
import exceptions.InvalidBookingDateException;
import exceptions.InvalidExceedPassengerCapacity;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;

/**
 * Description: This class represents a car in the ride sharing system.  
 * <br>
 * This class is created from Assignment 1 Solution.
 * <br>
 * Class: Car
 * @author Mr Rodney Cocker
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class Car {
	
	/**
	 * Description: These are the fields that describe a Car.
	 */
	// Car attributes
	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;

	// Tracking bookings
	protected Booking[] currentBookings;
	protected Booking[] pastBookings;
	private boolean available;
	protected int bookingSpotAvailable = 0;
	protected double tripFee = 0;
	
	// Constants
	private final double STANDARD_BOOKING_FEE = 1.5;
	private final int MAXIUM_PASSENGER_CAPACITY = 10;
	private final int MINIMUM_PASSENGER_CAPACITY = 1;

	/**
	 * Description: The class Constructor. It creates a Car.
	 * @param regNo The registration number of the Car.
	 * @param make The make of the Car.
	 * @param model The model of the Car.
	 * @param driverName The driver name of the Car.
	 * @param passengerCapacity The passenger capacity of the Car.
	 * @throws Exception The exceptions required.
	 */
	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) throws Exception {
		setRegNo(regNo); // Validates and sets registration number
		setPassengerCapacity(passengerCapacity); // Validates and sets passenger capacity

		this.make = make;
		this.model = model;
		this.driverName = driverName;
		available = true;
		currentBookings = new Booking[5];
		pastBookings = new Booking[10];
	}

	/* (Made by Mr Rodney Cocker)
	 * Checks to see if the booking is permissible such as a valid date, number of
	 * passengers, and general availability. Creates the booking only if conditions
	 * are met and assigns the trip fee to be equal to the standard booking fee.
	 * <br>
	 * <br>
	 * ALGORITHM BEGIN CHECK if car has five booking CHECK if car has a booking on
	 * date requested CHECK if the date requested is in the past. CHECK if the
	 * number of passengers requested exceeds the capacity of the car. IF any checks
	 * fail return false to indicate the booking operation failed ELSE CREATE the
	 * booking ADD the booking to the current booking array UPDATE the available
	 * status if there are now five current bookings. RETURN true to indicate the
	 * success of the booking.
	 * <br>
	 * <br>
	 * TEST Booking a car to carry 0, 10, & within/without passenger capacity.
	 * Booking car on date prior to today Booking a car on a date that is more than
	 * 7 days in advance. Booking car on a date for which it is already booked
	 * Booking six cars
	 */

	/**
	 * Description: This method is used for booking the Car.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param required The date that the user booked the Car.
	 * @param numPassengers The number of passengers that the user booked the Car.
	 * @return booked A boolean value representing if the Car can be booked.
	 * @throws Exception The exceptions required.
	 */
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) throws Exception {
		boolean booked = false;
		// Does car have five bookings
		available = bookingAvailable();
		boolean dateAvailable = notCurrentlyBookedOnDate(required);
		// Date is within range, not in past and within the next week
		boolean dateValid = dateIsValid(required);
		// Number of passengers does not exceed the passenger capacity and is not zero.
		boolean validPassengerNumber = numberOfPassengersIsValid(numPassengers);

		// Booking is permissible
		if (available && dateAvailable && dateValid && validPassengerNumber) {
			tripFee = STANDARD_BOOKING_FEE;
			Booking booking = new Booking(firstName, lastName, required, numPassengers, this);
			currentBookings[bookingSpotAvailable] = booking;
			bookingSpotAvailable++;
			booked = true;
		}
		
		if (!available) {
			throw new InvalidBookingCarFullExcception("Error: Car is full in bookings");
		}
		
		return booked;
	}
	
	/**
	 * Description: This method is used for the SilverServiceCar class when booking the Car.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param required The date that the user booked the Car.
	 * @param numPassengers The number of passengers that the user booked the Car.
	 * @param bookingFee The booking fee of the Car.
	 * @return booked A boolean value representing if the Car can be booked.
	 * @throws Exception The exceptions required.
	 */
	
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers, double bookingFee) throws Exception {
		boolean booked = false;
		// Does car have five bookings
		available = bookingAvailable();
		boolean dateAvailable = notCurrentlyBookedOnDate(required);
		// Date is within range, not in past and within the next week
		boolean dateValid = dateIsValid(required);
		// Number of passengers does not exceed the passenger capacity and is not zero.
		boolean validPassengerNumber = numberOfPassengersIsValid(numPassengers);

		// Booking is permissible
		if (available && dateAvailable && dateValid && validPassengerNumber) {
			tripFee = bookingFee;
			Booking booking = new Booking(firstName, lastName, required, numPassengers, this);
			currentBookings[bookingSpotAvailable] = booking;
			bookingSpotAvailable++;
			booked = true;
		}
		
		if (!available) {
			throw new InvalidBookingCarFullExcception("Error: Car is full in bookings");
		}
		
		return booked;
	}

	/* (Made by Mr Rodney Cocker)
	 * Completes a booking based on the name of the passenger and the booking date.
	 */
	
	/**
     * Description: This method completes a booking based on the name of the passenger and the booking date.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param dateOfBooking The date that the user booked the Car.
	 * @param kilometers The distance travelled by the Car.
	 * @return A string representing a statement if the booking is found or not.
	 */
	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {
		// Find booking in current bookings by passenger and date
		int bookingIndex = getBookingByDate(firstName, lastName, dateOfBooking);

		if (bookingIndex == -1) {
			return "Booking not found.";
		}

		return completeBooking(bookingIndex, kilometers);
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Completes a booking based on the name of the passenger.
	 */
	
	/**
	 * Description: This method completes a booking based on the name of the passenger.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param kilometers The distance travelled by the Car.
	 * @return A string representing a statement if the booking is found or not.
	 */
	public String completeBooking(String firstName, String lastName, double kilometers) {
		int bookingIndex = getBookingByName(firstName, lastName);

		if (bookingIndex == -1) {
			return "Booking not found.";
		} 
		
		else {
			return completeBooking(bookingIndex, kilometers);
		}
	}

	/* (Made by Mr Rodney Cocker)
	 * Checks the current bookings to see if any of the bookings are for the current
	 * date. ALGORITHM BEGIN CHECK All bookings IF date supplied matches date for
	 * any booking date Return true ELSE Return false END
	 */
	
	/**
	 * Description: This method checks if any of the car bookings are for the date the user entered.
	 * @param dateRequired The date that the user booked the Car.
	 * @return A boolean value representing if the car is booked on the date.
	 */
	public boolean isCarBookedOnDate(DateTime dateRequired) {
		boolean carIsBookedOnDate = false;
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] != null) {
				
				if (DateUtilities.datesAreTheSame(dateRequired, currentBookings[i].getBookingDate())) {
					carIsBookedOnDate = true;
				}
			}
		}
		
		return carIsBookedOnDate;
	}

	/* (Made by Mr Rodney Cocker)
	 * Retrieves a booking id based on the name and the date of the booking
	 */
	
	/**
	 * Description: This method returns the booking ID based on the name and date of the booking.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param dateOfBooking The date that the user booked the Car.
	 * @return A string representing the ID of the booking if the booking is found. 
	 */
	public String getBookingID(String firstName, String lastName, DateTime dateOfBooking) {
		System.out.println();
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] != null) {
				
				Booking booking = currentBookings[i];
				boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
				boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
				int days = DateTime.diffDays(dateOfBooking, booking.getBookingDate());
				
				if (firstNameMatch && lastNameMatch && days == 0) {
					return booking.getID();
				}
			}
		}
		return "Booking not found";
	}
	
	/**
	 * Description: This method gets the current bookings of the car.
	 * @return A string representing the details of the current bookings of the car.
	 */
	public String getCurrentBookings() {
		StringBuilder sb = new StringBuilder();
		if (currentBookings[0] != null) {
			for (int i = 0; i < currentBookings.length; i = i + 1) {
				if (currentBookings[i] != null) {
					sb.append(String.format("\n"));
					sb.append(String.format("Current Bookings"));
					sb.append(currentBookings[i].getDetails());
				}
			}
		}
		return sb.toString();
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Checks to see if any past bookings have been recorded
	 */
	
	/**
	 * Description: This method gets the past bookings of the car.
	 * @return A string representing the details of the past bookings of the car.
	 */
	public String getPastBookings() {
		StringBuilder sb = new StringBuilder();
		if (pastBookings[0] != null) {
			for (int i = 0; i < pastBookings.length; i = i + 1) {
				if (pastBookings[i] != null) {
					sb.append(String.format(""));
					sb.append(String.format("Past Bookings"));
					sb.append(pastBookings[i].getDetails());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Description: This method checks if the booking list of the Car is full.
	 * @param bookings The booking list of the car.
	 * @return A boolean value representing if the booking list is full.
	 */
	private boolean hasBookings(Booking[] bookings) {
		boolean found = false;
	
		for (int i = 0; i < bookings.length; i++) {
			if (bookings[i] != null) {
				found = true;
			}
		}
	
		return found;
	}

	/* (Made by Mr Rodney Cocker)
	 * Processes the completion of the booking
	 */
	
	/**
	 * Description: This method processes the completion of the booking.
	 * @param bookingIndex The specific booking in the booking list.
	 * @param kilometers The length of kilometers input by the user.
	 * @return A String representing a statement when the booking is completed.
	 */
	protected String completeBooking(int bookingIndex, double kilometers)
	{
		tripFee = 0;
		Booking booking = currentBookings[bookingIndex];
		
		// Remove booking from current bookings array.
		currentBookings[bookingIndex] = null;
		bookingSpotAvailable = bookingIndex;

		// call complete booking on Booking object
		// double kilometersTravelled = Math.random()* 100;
		double fee = kilometers * (STANDARD_BOOKING_FEE * 0.3);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, STANDARD_BOOKING_FEE);
		
		// add booking to past bookings
		for (int i = 0; i < pastBookings.length; i++) {
			if (pastBookings[i] == null) {
				pastBookings[i] = booking;
				break;
			}
		}
		String result = String.format("Thank you for riding with MiRide.\nWe hope you enjoyed your trip.\n$"
				+ "%.2f has been deducted from your account.", tripFee);
		tripFee = 0;
		return result;
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Gets the position in the array of a booking based on a name and date. Returns
	 * the index of the booking if found. Otherwise it returns -1 to indicate the
	 * booking was not found.
	 */
	
	/**
	 * Description: This method checks if there is a booking based on the user's first and last name, and the date of the booking.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param dateOfBooking The date that the user booked the Car.
	 * @return An integer representing if there is a date of such booking.
	 */
	protected int getBookingByDate(String firstName, String lastName, DateTime dateOfBooking) {
		System.out.println();
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] != null) {
				Booking booking = currentBookings[i];
				boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
				boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
				boolean dateMatch = DateUtilities.datesAreTheSame(dateOfBooking, currentBookings[i].getBookingDate());
				
				if (firstNameMatch && lastNameMatch && dateMatch) {
					return i;
				}
			}
		}
		return -1;
	}

	/* (Made by Mr Rodney Cocker)
	 * Gets the position in the array of a booking based on a name. Returns the
	 * index of the booking if found. Otherwise it returns -1 to indicate the
	 * booking was not found.
	 */
	
	/**
	 * Description: This method checks if there is a booking based on the user's first and last name.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @return An integer representing if there is a name of such booking.
	 */
	public int getBookingByName(String firstName, String lastName) {
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] != null) {
				boolean firstNameMatch = currentBookings[i].getFirstName().toUpperCase()
						.equals(firstName.toUpperCase());
				boolean lastNameMatch = currentBookings[i].getLastName().toUpperCase().equals(lastName.toUpperCase());
				
				if (firstNameMatch && lastNameMatch) {
					return i;
				}
			}
		}
		return -1;
	}

	/* (Made by Mr Rodney Cocker)
	 * A record marker mark the beginning of a record.
	 */
	protected String getRecordMarker() {
		final int RECORD_MARKER_WIDTH = 60;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < RECORD_MARKER_WIDTH; i++) {
			sb.append("_");
		}
		
		sb.append("\n");
		return sb.toString();
	}

	/* (Made by Mr Rodney Cocker)
	 * Checks to see if the number of passengers falls within the accepted range.
	 */
	
	/**
	 * Description: This method checks to see if the number of passengers falls within the accepted range.
	 * @param numPassengers The number of passengers that is indicated by the user.
	 * @return A boolean value representing if the number of passengers the user requires exceeds the number of seats in the car.
	 * @throws Exception The exceptions required.
	 */
	protected boolean numberOfPassengersIsValid(int numPassengers) throws Exception {
		if (numPassengers >= MINIMUM_PASSENGER_CAPACITY && numPassengers < MAXIUM_PASSENGER_CAPACITY
				&& numPassengers <= passengerCapacity) {
			return true;
		}
		throw new InvalidExceedPassengerCapacity("Error: Passenger Capacity exceeds car size.");
//		return false;
	}

	/* (Made by Mr Rodney Cocker)
	 * Checks that the date is not in the past or more than 7 days in the future.
	 */
	
	/**
	 * Description: This method checks to see if the date entered by the user is not in the past or more than 7 days in the future.
	 * @param date The date entered by the user.
	 * @return A boolean value representing if the date the user entered is valid.
	 * @throws Exception The exceptions required.
	 */
	protected boolean dateIsValid(DateTime date) throws Exception {
		if (DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan7Days(date)) {
			return true;
		}
		throw new InvalidBookingDateException("Error: Date is invalid, must be within the coming week.");
//		return false;
	}

	/* (Made by Mr Rodney Cocker)
	 * Indicates if a booking spot is available. If it is then the index of the
	 * available spot is assigned to bookingSpotFree.
	 */
	
	/**
	 * Description: This method checks if there is any booking spots available.
	 * @return A boolean value representing if there is any booking spots available.
	 */
	protected boolean bookingAvailable() {
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] == null) {
				
				if(i == currentBookings.length - 1) {
					available = false;
				}
				
				else {
					available = true;
				}
				bookingSpotAvailable = i;
				return true;
			}
		}
		return false;
	}

	/* (Made by Mr Rodney Cocker)
	 * Checks to see if if the car is currently booked on the date specified.
	 */
	
	/**
	 * Description: This method Checks to see if the Car is currently booked on the date specified.
	 * @param date The date entered by the user.
	 * @return A boolean value representing if the Car is currently booked on the date.
	 */
	protected boolean notCurrentlyBookedOnDate(DateTime date)
	{
		boolean foundDate = true;
		
		for (int i = 0; i < currentBookings.length; i++) {
			
			if (currentBookings[i] != null) {
				int days = DateTime.diffDays(date, currentBookings[i].getBookingDate());
				
				if (days == 0){
					return false;
				}
			}
		}
		return foundDate;
	}

	/* (Made by Mr Rodney Cocker)
	 * Validates and sets the registration number
	 */
	
	/**
	 * Description: This method validates and sets the registration number.
	 * @param regNo The registration number of the Car.
	 * @throws Exception The exceptions required.
	 */
	private void setRegNo(String regNo) throws Exception {
		if (!MiRidesUtilities.isRegNoValid(regNo).contains("Error:")) {
			this.regNo = regNo;
		}  
		else {
			this.regNo = "Invalid";
		}
	}

	/* (Made by Mr Rodney Cocker)
	 * Validates and sets the passenger capacity
	 */
	
	/**
	 * Description: This method validates and sets the registration number.
	 * @param passengerCapacity The number of seats of the Car.
	 */
	private void setPassengerCapacity(int passengerCapacity) {
		boolean validPasengerCapcity = passengerCapacity >= MINIMUM_PASSENGER_CAPACITY
				&& passengerCapacity < MAXIUM_PASSENGER_CAPACITY;

		if (validPasengerCapcity) {
			this.passengerCapacity = passengerCapacity;
		} 
		
		else {
			this.passengerCapacity = -1;
		}
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Human readable presentation of the state of the car.
	 */
	
	/**
	 * Description: This method is used by other methods to get the details of the Car.
	 * @return A string representing the details of the Car.
	 */
	public String getDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append(getRecordMarker());
		sb.append(String.format("%-15s %s\n", "Reg No:", regNo));
		sb.append(String.format("%-15s %s\n", "Make & Model:", make + " " + model));
		sb.append(String.format("%-15s %s\n", "Driver Name:", driverName));
		sb.append(String.format("%-15s %s\n", "Capacity:", passengerCapacity));
		sb.append(String.format("%-15s %s\n", "Standard Fee:", STANDARD_BOOKING_FEE));

		if (bookingAvailable()) {
			sb.append(String.format("%-15s %s\n", "Available:", "YES"));
		} 
		
		else {
			sb.append(String.format("%-15s %s\n", "Available:", "NO"));
		}
		
		if (currentBookings[0] != null) {
			sb.append(getCurrentBookings());
		}
		
		if (pastBookings[0] != null) {
			sb.append(getPastBookings());
		}
		
		return sb.toString();
	}
	
	/* (Made by Mr Rodney Cocker)
	 * Computer readable state of the car
	 */
	
	/**
	 * Description: This method is used by other methods to get the string of the Car all in one line.
	 * @return A string representing the details of the Car all in one line, separated by a colon ':'.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(regNo + ":" + make + ":" + model);
		
		if (driverName != null) {
			sb.append(":" + driverName);
		}
		sb.append(":" + passengerCapacity);
		
		if (bookingAvailable()) {
			sb.append(":" + "YES");
		} 
		
		else {
			sb.append(":" + "NO");
		}

		return sb.toString();
	}

	// Required getters
	
	/**
	 * Description: This method returns the registration number of the Car.
	 * @return A string representing the registration number of the Car.
	 */
	public String getRegistrationNumber() {
		return regNo;
	}

	/**
	 * Description: This method returns the driver name of the Car.
	 * @return A string representing the driver name of the Car.
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Description: This method returns the driver name of the Car.
	 * @return A string representing the driver name of the Car.
	 */
	public double getTripFee() {
		return tripFee;
	}
	
	/**
	 * Description: This method returns the make of the Car.
	 * @return A string representing the make of the Car.
	 */
	public String getMake() {
		return make;
	}
	
	/**
	 * Description: This method returns the model of the Car.
	 * @return A string representing the model of the Car.
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * Description: This method returns the number of seats in the Car.
	 * @return An integer representing the number of seats in the Car.
	 */
	public int getPassengerCapacity() {
		return passengerCapacity;
	}
}
