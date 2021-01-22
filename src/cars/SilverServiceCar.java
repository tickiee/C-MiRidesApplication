package cars;

import cars.Car;
import utilities.DateTime;
import utilities.DateUtilities;

/**
 * Description:	This class represents a car in the ride sharing system. 
 * <br>
 * Class: SilverServiceCar 
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class SilverServiceCar extends Car {
	
	/**
	 * Description: These are the fields that describe a Silver Service Car.
	 */
	private double bookingFee;
	private String[] drinks = new String[10];
	
	/**
	 * Description: The class Constructor. It creates a Silver Service Car.
	 * @param regNo The registration number of the Silver Service Car.
	 * @param make The make of the Silver Service Car.
	 * @param model The model of the Silver Service Car.
	 * @param driverName The driver name of the Silver Service Car.
	 * @param passengerCapacity The passenger capacity of the Silver Service Car.
	 * @param bookingFee The booking fee of the Silver Service Car.
	 * @param refreshments The refreshments provided by the Silver Service Car.
	 * @throws Exception The exceptions required.
	 */
	public SilverServiceCar(String regNo, String make,String model, String driverName, int passengerCapacity, double bookingFee, String[] refreshments) throws Exception {
		super(regNo, make, model, driverName, passengerCapacity);
		
		// If the booking fee is less than $3
		if (bookingFee < 3) {
			System.out.println("Error");
			System.out.println("Booking fee must be greater or equal to $3.00");
		}
		
		else {
			this.bookingFee = bookingFee;
		}
		
		int index = 0;
		
		for (int i = 0; i < refreshments.length; i = i + 1) {
			drinks[index] = refreshments[i];
			index = index + 1;
		}
	}
	
	/**
	 * Description: This method returns the booking fee of the Silver Service Car.
	 * @return A double representing the booking fee.
	 */
	public double getBookingFee() {
		return bookingFee;
	}
	
	/**
	 * Description: This method is used to get refreshments of the Silver Service Car. 
	 * <br>
	 * This method is used for the Persistence class.
	 * @return A string representing the refreshments in the format of example, "Abc,Def,Ghi"
	 */
	public String getRefreshmentsForSaveFile() {
		String refreshments = "";
		
		for (int i = 0; i < drinks.length; i = i + 1) {
			
			if (drinks[i] != null) {
				refreshments = refreshments + drinks[i] + ",";
			}
		}
		
		return refreshments.substring(0, refreshments.length() - 1);
	}

	/**
	 * Description: This method is used for booking the Silver Service Car.
	 * <br>
	 * This method is an override method from the Car class.
	 * @param firstName The first name of the user booking the Silver Service Car.
	 * @param lastName The last name of the user booking the Silver Service Car.
	 * @param required The date that the user booked the Silver Service Car.
	 * @param numPassengers The number of passengers that the user booked the Silver Service Car.
	 * @return A boolean value representing if the Silver Service Car can be booked.
	 * @throws Exception The exceptions required.
	 */
	@Override
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) throws Exception {
		boolean booked = false;
		boolean dateThreeDays;
		dateThreeDays = DateUtilities.dateIsNotMoreThan3Days(required);
		
		if (!dateThreeDays) {
			System.out.println("Error");
			System.out.println("Date must be 3 days or less");
			booked = false;
		}
		
		else {
			booked = super.book(firstName, lastName, required, numPassengers, bookingFee);
		}
		
		return booked;
	}
		
	/**
	 * Description: This method processes the completion of the booking
	 * <br>
	 * This method is an override method from the Car class.
	 * @param bookingIndex This variable checks which is the booking in the Silver Service Car's booking array.
	 * @param kilometers The distance travelled by the Silver Service Car.
	 * @return result A string representing the statement of the user's trip and its cost.
	 */
	@Override
	protected String completeBooking(int bookingIndex, double kilometers) {
		tripFee = 0;
		Booking booking = currentBookings[bookingIndex];
		
		// Remove booking from current bookings array.
		currentBookings[bookingIndex] = null;
		bookingSpotAvailable = bookingIndex;

		// call complete booking on Booking object
		// double kilometersTravelled = Math.random()* 100;
		double fee = kilometers * (bookingFee * 0.4);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, bookingFee);
		
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
	
	/**
	 * Description: This method completes a booking based on the name of the passenger and the booking date.
	 * <br>
	 * This method is an override method from the Car class.
	 * @param firstName The first name of the user booking the Silver Service Car.
	 * @param lastName The last name of the user booking the Silver Service Car.
	 * @param dateOfBooking The date that the user booked the Silver Service Car.
	 * @param kilometers The distance travelled by the Silver Service Car.
	 * @return A string representing a statement if the booking is found or not.
	 */
	@Override
	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {
			// Find booking in current bookings by passenger and date
			int bookingIndex = getBookingByDate(firstName, lastName, dateOfBooking);
	
			if (bookingIndex == -1) {
				return "Booking not found.";
			}
	
			return completeBooking(bookingIndex, kilometers);
	}
	
	/**
	 * Description: This method completes a booking based on the name of the passenger.
	 * <br>
	 * This method is an override method from the Car class.
	 * @param firstName The first name of the user booking the Silver Service Car.
	 * @param lastName The last name of the user booking the Silver Service Car.
	 * @param kilometers The distance travelled by the Silver Service Car.
	 * @return A string representing a statement if the booking is found or not.
	 */
	@Override
	public String completeBooking(String firstName, String lastName, double kilometers) {
		int bookingIndex = getBookingByName(firstName, lastName);

		if (bookingIndex == -1) {
			return "Booking not found.";
		} 
		
		else {
			return completeBooking(bookingIndex, kilometers);
		}
	}
	
	/**
	 * Description: This method is used by other methods to get the details of the Silver Service Car.
	 * <br>
	 * This method is an override method from the Car class.
	 * @return A string representing the details of the Silver Service Car.
	 */
	@Override
	public String getDetails() {
		StringBuilder sb = new StringBuilder();
		String regNo = super.getRegistrationNumber();
		String make = super.getMake();
		String model = super.getModel();
		String driverName = super.getDriverName();
		int passengerCapacity = super.getPassengerCapacity();
		
		sb.append(getRecordMarker());
		sb.append(String.format("%-15s %s\n", "Reg No:", regNo));
		sb.append(String.format("%-15s %s\n", "Make & Model:", make + " " + model));
		sb.append(String.format("%-15s %s\n", "Driver Name:", driverName));
		sb.append(String.format("%-15s %s\n", "Capacity:", passengerCapacity));
		sb.append(String.format("%-15s %s\n", "Standard Fee:", bookingFee));
		
		if(bookingAvailable()) {
			sb.append(String.format("%-15s %s\n", "Available:", "YES"));
		} 
		
		else {
			sb.append(String.format("%-15s %s\n", "Available:", "NO"));
		}
		
		sb.append(String.format("\n"));
		sb.append(getRefreshments());
		sb.append(String.format("\n"));
		
		if (super.currentBookings[0] != null) {
			sb.append(getCurrentBookings());
		}
		
		if (super.pastBookings[0] != null) {
			sb.append(getPastBookings());
		}
		
		return sb.toString();
	}
	
	/**
	 * Description: This method is used by the getDetails method to display the refreshments provided by the Silver Service Car.
	 * @return A string representing the refreshments of the Silver Service Car.
	 */
	public String getRefreshments() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Refreshments Available: "));
		sb.append(String.format("\n"));
		int index = 1;
		
		for (int i = 0; i < drinks.length; i = i + 1) {
			
			if (drinks[i] != null) {
				sb.append(String.format("Item " + index + ":\t" + drinks[i]));
				sb.append(String.format("\n"));
				index = index + 1;
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Description: This method is used by the toString method to display the refreshments provided by the Silver Service Car.
	 * @return A string representing the refreshments of the Silver Service Car.
	 */
	public String getRefreshmentsForToString() {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		
		for (int i = 0; i < drinks.length; i = i + 1) {
			
			if (drinks[i] != null) {
				sb.append(String.format("Item " + index + " " + drinks[i] + ":"));
				index = index + 1;
			}
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	/**
	 * Description: This method is used by other methods to get the string of the Silver Service Car all in one line.
	 * <br>
 	 * This method is an override method from the Car class.
	 * @return A string representing the details of the Silver Service Car all in one line, separated by a colon ':'.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String regNo = super.getRegistrationNumber();
		String make = super.getMake();
		String model = super.getModel();
		String driverName = super.getDriverName();
		int passengerCapacity = super.getPassengerCapacity();
		
		sb.append(regNo + ":" + make + ":" + model + ":" + bookingFee + ":" + getRefreshmentsForToString());
		
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
}
