package app;

import java.io.IOException;
import cars.Car;
import cars.SilverServiceCar;
import exceptions.InvalidRefreshmentsException;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;
import utilities.Persistence;

/*
 * Class:			MiRideApplication
 * Description:		The system manager the manages the 
 *              	collection of data. 
 * Author:			Rodney Cocker
 */

/**
 * Description:	This class manages the collection of data.
 * <br>
 * This class is created from Assignment 1 Solution.
 * <br>
 * Class: MiRideApplication
 * @author Mr Rodney Cocker
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class MiRideApplication {
	
	private Car[] cars = new Car[15];
	private int itemCount = 0;
	private String[] availableCars;

	/**
	 * Description: This method is a default constructor.
	 */
	public MiRideApplication() {
		//seedData();
	}
	
	//Overloading
	/**
	 * Description: This method is used to create the Silver Service Car.
	 * @param id The registration number of the Silver Service Car.
	 * @param make The make of the Silver Service Car.
	 * @param model The model of the Silver Service Car.
	 * @param driverName The driver name of the Silver Service Car.
	 * @param numPassengers The passenger capacity of the Silver Service Car.
	 * @param bookingFee The booking fee of the Silver Service Car.
	 * @param refreshments The refreshments provided by the Silver Service Car.
	 * @return A string representing if the Silver Service Car is successfully created.
	 * @throws Exception The exceptions required.
	 */
	public String createCar(String id, String make, String model, String driverName, int numPassengers, 
							double bookingFee, String refreshments) throws Exception {
		String validId = isValidId(id);
		
		if (isValidId(id).contains("Error:")) {
			return validId;
		}
		
		if (!checkIfCarExists(id)) {
			String[] refreshmentsList = refreshments.split(",");
			
			if (refreshmentsList.length < 3) {
				throw new InvalidRefreshmentsException("Error: 3 or more refreshments is required.");
			}
			
			for (int i = 0; i < refreshmentsList.length; i++) {
				
				if (refreshmentsList[i] != null) {
					
					if (refreshmentsList[i].equals("")) {
						throw new InvalidRefreshmentsException("Error: Refreshment is empty.");
					}
				}
			}			
			
			int j = 1;
			
			for (int i = 0; i < refreshmentsList.length; i++) {
				
				for (int value = j; value <= refreshmentsList.length - 1; value++) {
					
					if (refreshmentsList[i].toUpperCase().equals(refreshmentsList[j].toUpperCase())) {
						throw new InvalidRefreshmentsException("Error: Duplicated refreshments in the list.");
					}
				}
				
				if (j >= refreshmentsList.length) {
					break;
				}
				
				else {
					j++;
				}
			}
			
			cars[itemCount] = new SilverServiceCar(id, make, model, driverName, numPassengers, bookingFee, refreshmentsList);
			itemCount++;
			
			return "New Car added successfully for registion number: " + cars[itemCount-1].getRegistrationNumber();
		}
		
		return "Error: Already exists in the system.";
	}

	/**
	 * Description: This method is used to display the cars available on the date that the user wanted to book the car.
	 * @param dateRequired The date that the user entered.
	 * @return A string of the cars that can be booked on the date entered by the user.
	 */
	public String[] book(DateTime dateRequired) {
		int numberOfAvailableCars = 0;
		// finds number of available cars to determine the size of the array required.
		
		for (int i=0; i<cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (!cars[i].isCarBookedOnDate(dateRequired)) {
					numberOfAvailableCars++;
				}
			}
		}
		
		if (numberOfAvailableCars == 0) {
			String[] result = new String[0];
			return result;
		}
		
		availableCars = new String[numberOfAvailableCars];
		int availableCarsIndex = 0;
		
		// Populate available cars with registration numbers
		for (int i=0; i<cars.length;i++) {
			
			if (cars[i] != null) {
				
				if (!cars[i].isCarBookedOnDate(dateRequired)) {
					availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getRegistrationNumber();
					availableCarsIndex++;
				}
			}
		}
		
		return availableCars;
	}
	
	/**
	 * Description: This method creates the booking of the car chosen by the user.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param required The date that the user booked the Car.
	 * @param numPassengers The number of passengers that the user booked the Car.
	 * @param registrationNumber The registration number of the Car.
	 * @return A string representing if the Car was able to be booked.
	 * @throws Exception The exceptions required.
	 */
	public String book(String firstName, String lastName, DateTime required, int numPassengers, String registrationNumber) throws Exception {
		Car car = getCarById(registrationNumber);
		
		if (car != null) {
			
			if (car.book(firstName, lastName, required, numPassengers)) {

				String message = "Thank you for your booking. \n" + car.getDriverName() 
		        + " will pick you up on " + required.getFormattedDate() + ". \n"
				+ "Your booking reference is: " + car.getBookingID(firstName, lastName, required);
				return message;
			}
			
			else {
				String message = "Booking could not be completed.";
				return message;
			}
        }
		
        else {
            return "Car with registration number: " + registrationNumber + " was not found.";
        }
	}
	
	/**
	 * Description: This method checks if there is a car that can be booked by the date requested by the user.
	 * @param dateRequired The date that the user want to book the car.
	 * @param type The type of the car, standard or Silver Service.
	 */
	public void book(DateTime dateRequired, String type) {
		int numberOfAvailableCars = 0;
		
		// finds number of available cars to determine the size of the array required.
		for (int i=0; i<cars.length; i++) {
			
			if (cars[i] != null) {
				
				if(!cars[i].isCarBookedOnDate(dateRequired)) {
					numberOfAvailableCars++;
				}
			}
		}
		
		if (numberOfAvailableCars == 0) {
			System.out.println("No cars available on this date.");
		}
		
		availableCars = new String[numberOfAvailableCars];
		int availableCarsIndex = 0;
		
		// Populate available cars with registration numbers
		boolean dateThreeDays;
		dateThreeDays = DateUtilities.dateIsNotMoreThan3Days(dateRequired);
		
		for (int i=0; i<cars.length;i++) {
			
			if (cars[i] != null) {
				
				if (!cars[i].isCarBookedOnDate(dateRequired)) {
				
					if ((cars[i].getClass().equals(Car.class)) && type.equals("SD")) {
						Car sdCars = cars[i];
						System.out.println(sdCars.getDetails());
					}
					
					else if ((cars[i] instanceof SilverServiceCar) && type.equals("SS") &&
							dateThreeDays) {
						SilverServiceCar ssCars = (SilverServiceCar) cars[i];
						System.out.println(ssCars.getDetails());
					}
				}
			}
		}
	}
	
	/**
     * Description: This method completes a booking based on the name of the passenger and the booking date.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param dateOfBooking The date that the user booked the Car.
	 * @param kilometers The distance travelled by the Car.
	 * @return A string representing if the booking was completed successfully.
	 */
	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {
		String result = "";
		
		// Search all cars for bookings on a particular date.
		for (int i = 0; i <cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (cars[i].isCarBookedOnDate(dateOfBooking)) {
					return cars[i].completeBooking(firstName, lastName, dateOfBooking, kilometers);
				}
				
//				else {
//					
//				}
//				
//				if(!result.equals("Booking not found") {
//					return result;
//				}
			}
		}
		
		return "Booking not found.";
	}
	
	/**
     * Description: This method completes a booking based on the name of the passenger and the registration number of the Car.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param registrationNumber The registration number of the Car the user travelled in.
	 * @param kilometers The distance travelled by the Car.
	 * @return A string representing if the booking was completed successfully.
	 */
	public String completeBooking(String firstName, String lastName, String registrationNumber, double kilometers) {
		String carNotFound = "Car not found";
		Car car = null;
		// Search for car with registration number
		
		for (int i = 0; i <cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (cars[i].getRegistrationNumber().equals(registrationNumber)) {
					car = cars[i];
					break;
				}
			}
		}

		if (car == null) {
			return carNotFound;
		}
		
		if (car.getBookingByName(firstName, lastName) != -1) {
			return car.completeBooking(firstName, lastName, kilometers);
		}
		
		return "Error: Booking not found.";
	}
	
	/**
	 * Description: This method displays all the cars details, current and past bookings.
	 * @param type The type of the car, standard or Silver Service.
	 * @param sortOrder The order of registration number, ascending or decending requested by the user. 
	 * @return A string representing all the car details, current and past bookings.
	 */
	public String displayAllBookings(String type, String sortOrder) {
		StringBuilder sb = new StringBuilder();
		boolean sorted = false;	
		int itemCount = 0;
		
		for (int i = 0; i < cars.length; i = i + 1) {
			
			if (cars[i] != null) {
				itemCount = itemCount + 1;
			}
		}
		
		if (sortOrder.equals("A")) {
			
			while(!sorted) {
				//check if the array is already sorted
				sorted = true;
				
				//for i & i + 1
				for (int i = 0; i < itemCount - 1; i = i + 1) {
					if (cars[i].getRegistrationNumber().compareTo(cars[i + 1].getRegistrationNumber()) > 0) {
					Car temp = cars[i + 1];
					cars[i + 1] = cars[i];
					cars[i] = temp;
					sorted = false;
					}
				}	
			}
		}
		
		else if (sortOrder.equals("D")) {
			
			while(!sorted) {
				//check if the array is already sorted
				sorted = true;
				
				//for i & i + 1
				for (int i = 0; i < itemCount - 1; i = i + 1) {
					
					if (cars[i + 1].getRegistrationNumber().compareTo(cars[i].getRegistrationNumber()) > 0) {
					Car temp = cars[i];
					cars[i] = cars[i + 1];
					cars[i + 1] = temp;
					sorted = false;
					}
				}	
			}
		}
		
		if (type.equals("SD")) {
			
			if (itemCount == 0) {
				return "No cars have been added to the system.";
			}
			
			sb.append("Summary of all Cars: ");
			sb.append("\n");
			
			for (int i = 0; i < itemCount; i++) {
				
				// Checks if the car is a standard Car.
				if ((cars[i].getClass().equals(Car.class))) {
					Car sdCars = cars[i];
					sb.append(sdCars.getDetails());
				}
			}
		}
		
		if (type.equals("SS")) {
			
			if(itemCount == 0) {
				return "No cars have been added to the system.";
			}
			
			sb.append("Summary of all Silver Service Cars: ");
			sb.append("\n");
			
			for (int i = 0; i < itemCount; i++) {
				
				//Checks if the car is a Silver Service Car
				if (cars[i] instanceof SilverServiceCar) {
					SilverServiceCar ssCars = (SilverServiceCar) cars[i];
					sb.append(ssCars.getDetails());
				}
			}
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Description: This method displays the details of a car based on its registration number.
	 * @param regNo The registration number of the Car.
	 * @return A string showing the details of the Car if it can be found in the application.
	 */
	public String displaySpecificCar(String regNo) {
		
		for (int i = 0; i < cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (cars[i].getRegistrationNumber().equals(regNo)) {
					return cars[i].getDetails();
				}
			}
		}
		
		return "Error: The car could not be located.";
	}
	
//	/**
//	 * Description: This method checks if the cars available is a standard or a Silver Service Car.
//	 * @param type The type of the car, standard or Silver Service.
//	 */
//	public void availableCars(String type) {
//		
//		if (type.equals("SD")) {
//			
//			for (int i = 0; i < itemCount; i++) {
//				
//				if ((cars[i].getClass().equals(Car.class))) {
//					Car sdCars = cars[i];
//					System.out.println(sdCars.getDetails());
//				}
//			}
//		}
//		
//		if (type.equals("SS")) {
//			
//			for (int i = 0; i < itemCount; i++) {
//				
//				if (cars[i] instanceof SilverServiceCar) {
//					SilverServiceCar ssCars = (SilverServiceCar) cars[i];
//					System.out.println(ssCars.getDetails());
//				}
//			}
//		}		
//	}

//	public String displayBooking(String id, String seatId) {
//		Car booking = getCarById(id);
//		
//		if (booking == null) {
//			return "Booking not found";
//		}
//		
//		return booking.getDetails();
//	}
	
	/**
	 * Description: This method checks if the registration number when creating the car is valid.
	 * @param id The registration number when creating the car.
	 * @return A string representing the registration number of the car only if it is valid. 
	 * @throws Exception The exceptions required.
	 */
	public String isValidId(String id) throws Exception {
		return MiRidesUtilities.isRegNoValid(id);
	}
	
    /**
	 * Description: This method checks if the number of seats when creating the car is valid.
     * @param passengerNumber The number of seats in the car.
     * @return A string that approves the number of seats in the car only if it is valid.
     * @throws Exception The exceptions required.
     */
	public String isValidPassengerCapacity(int passengerNumber) throws Exception {
		return MiRidesUtilities.isPassengerCapacityValid(passengerNumber);
	}

	/**
	 * Description: This method checks if the booking fee of the Silver Service Car is above $3.
	 * @param fee The booking fee entered when creating the Silver Service Car.
	 * @return A boolean value representing if the booking fee is valid when creating the Silver Service Car.
	 */
	public boolean bookingFeeSilverServiceCar(double fee) {
		if (fee < 3) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Description: This method checks if there is already a registration number that is similar in the application when creating a car. 
	 * @param regNo The registration number of the car.
	 * @return A boolean value representing if there is already a registration number that is similar in the application when creating a car.
	 */
	public boolean checkIfCarExists(String regNo) {
		Car car = null;
		
		if (regNo.length() != 6) {
			return false;
		}
		
		car = getCarById(regNo);
		
		if (car == null) {
			return false;
		}
		
		else {
			return true;
		}
	}
	
	/**
	 * Description: This method find a specific car from the application based on the registration number.
	 * @param regNo The registration number of the Car.
	 * @return The specific car based on the registration number.
	 */
	private Car getCarById(String regNo) {
		Car car = null;

		for (int i = 0; i < cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (cars[i].getRegistrationNumber().equals(regNo)) {
					car = cars[i];
					return car;
				}
			}
		}
		
		return car;
	}
	
	/**
	 * Description: This method checks if the booking details entered by the user are found in the application.
	 * @param firstName The first name of the user booking the Car.
	 * @param lastName The last name of the user booking the Car.
	 * @param registrationNumber The registration number of the Car the user travelled in.
	 * @return A boolean value representing if the booking details match what the user entered.
	 */
	public boolean getBookingByName(String firstName, String lastName, String registrationNumber) {
		String bookingNotFound = "Error: Booking not found";
		Car car = null;
		// Search for car with registration number
		
		for (int i = 0; i <cars.length; i++) {
			
			if (cars[i] != null) {
				
				if (cars[i].getRegistrationNumber().equals(registrationNumber)) {
					car = cars[i];
					break;
				}
			}
		}
		
		if (car == null) {
			return false;
		}
		
		if (car.getBookingByName(firstName, lastName) == -1) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Description: This method creates 6 Cars and 6 Silver Service Cars.
	 * @return A boolean value representing if the method was able to run successfully.
	 * @throws Exception The exceptions required.
	 */
	public boolean seedData() throws Exception {
		
		for (int i = 0; i < cars.length; i++) {
			
			if (cars[i] != null) {
				System.out.println("Seed Data was cancelled");
				return false;
			}
		}
		
		// 2 cars not booked
		Car honda = new Car("SIM194", "Honda", "Accord Euro", "Henry Cavill", 5);
		cars[itemCount] = honda;
//		honda.book("Craig", "Cocker", new DateTime(1), 3);
		itemCount++;
		
		Car lexus = new Car("LEX666", "Lexus", "M1", "Angela Landsbury", 3);
		cars[itemCount] = lexus;
//		lexus.book("Craig", "Cocker", new DateTime(1), 3);
		itemCount++;
		
		// 2 cars booked
		Car bmw = new Car("BMW256", "Mini", "Minor", "Barbara Streisand", 4);
		cars[itemCount] = bmw;
		itemCount++;
		bmw.book("Craig", "Cocker", new DateTime(1), 3);

		Car audi = new Car("AUD765", "Mazda", "RX7", "Matt Bomer", 6);
		cars[itemCount] = audi;
		itemCount++;
		audi.book("Rodney", "Cocker", new DateTime(1), 4);
		
		// 1 car booked five times (not available)
		Car toyota = new Car("TOY765", "Toyota", "Corola", "Tina Turner", 7);
		cars[itemCount] = toyota;
		itemCount++;
		toyota.book("Rodney", "Cocker", new DateTime(1), 3);
		toyota.book("Craig", "Cocker", new DateTime(2), 7);
		toyota.book("Alan", "Smith", new DateTime(3), 3);
		toyota.book("Carmel", "Brownbill", new DateTime(4), 7);
		toyota.book("Paul", "Scarlett", new DateTime(5), 7);
//		toyota.book("Paul", "Scarlett", new DateTime(6), 7);
//		toyota.book("Paul", "Scarlett", new DateTime(7), 7);
		
		// 1 car booked five times (not available)
		Car rover = new Car("ROV465", "Honda", "Rover", "Jonathon Ryss Meyers", 7);
		cars[itemCount] = rover;
		itemCount++;
		rover.book("Rodney", "Cocker", new DateTime(1), 3);
//		rover.completeBooking("Rodney", "Cocker", 75);
		DateTime inTwoDays = new DateTime(2);
		rover.book("Rodney", "Cocker", inTwoDays, 3);
		rover.completeBooking("Rodney", "Cocker", inTwoDays,75);
		
		//SilverServiceCars
		// 2 cars not booked
		String[] refreshmentsCristopher = {"Apple Juice", "Mineral Water", "Coffee"};
		SilverServiceCar hondaCristopher = new SilverServiceCar("WYO094", "Honda", "Accord Euro", "Cristopher Harder", 8, 4, refreshmentsCristopher);
		cars[itemCount] = hondaCristopher;
		itemCount++;
		
		String[] refreshmentsJulianna = {"Lemonade", "Tea", "Mineral Water"};
		SilverServiceCar lexusJulianna = new SilverServiceCar("SCE241", "Lexus", "M1", "Julianna Coyne", 6, 3.5, refreshmentsJulianna);
		cars[itemCount] = lexusJulianna;
		itemCount++;
		
		// 2 cars booked
		String[] refreshmentsDusty = {"Grape Juice", "Chocolate Bar", "Coffee"};
		SilverServiceCar bmwDusty = new SilverServiceCar("GEU451", "Mini", "Minor", "Dusty Herman", 4, 5, refreshmentsDusty);
		cars[itemCount] = bmwDusty;
		itemCount++;
		bmwDusty.book("Craig", "Cocker", new DateTime(1), 2);
		
		String[] refreshmentsLiana = {"Mineral Water", "Coffee", "Apple Pie"};
		SilverServiceCar audiLiana = new SilverServiceCar("SCG269", "Mazda", "RX7", "Liana Breaux", 8, 3, refreshmentsLiana);
		cars[itemCount] = audiLiana;
		itemCount++;
		audiLiana.book("Rodney", "Cocker", new DateTime(1), 2);
		audiLiana.book("Craig", "Cocker", new DateTime(2), 3);
		
		// 2 cars booked five times (not available)
		String[] refreshmentsEloy = {"Orange Juice", "BBQ Potato Chips", "Coffee", "Tea"};
		SilverServiceCar toyotaCorola = new SilverServiceCar("SKA034", "Toyota", "Corola", "Eloy Mueller", 4, 3, refreshmentsEloy);
		cars[itemCount] = toyotaCorola;
		itemCount++;
		DateTime inOneDays = new DateTime(1);
		toyotaCorola.book("Rodney", "Cocker", new DateTime(0), 1);
		toyotaCorola.book("Rodney", "Cocker", new DateTime(1), 2);
		toyotaCorola.completeBooking("Rodney", "Cocker", inOneDays,75);
		
		String[] refreshmentsKatrice = {"Hot Chocolate", "Chocolate Rolls", "Mineral Water"};
		SilverServiceCar roverKatrice = new SilverServiceCar("HFM372", "Honda", "Rover", "Katrice Grayson", 8, 4.5, refreshmentsKatrice);
		cars[itemCount] = roverKatrice;
		itemCount++;
		roverKatrice.book("Craig", "Cocker", new DateTime(0), 1);
		roverKatrice.book("Craig", "Cocker", new DateTime(1), 2);
		roverKatrice.completeBooking("Craig", "Cocker", inOneDays,75);
		
		System.out.println("Seed Data was completed");
		return true;
	}
	
	/**
	 * Description: This method is used by the Persistence class to save cars when the user exits. 
	 * @throws IOException The exceptions required.
	 */
	public void saveCars() throws IOException {
		Persistence fileWriting = new Persistence();
		fileWriting.saveCar(cars);
	}
	
	/**
	 * Description: This method is used by the Persistence class to save cars when the user exits.
	 * @throws Exception The exceptions required.
	 */
	public void loadCars() throws Exception {
			Persistence fileWriting = new Persistence();
			cars = fileWriting.readData("backupcarsavefile.txt");
			
			for (int i = 0; i < cars.length; i = i + 1) {
				
				if (cars[i] != null) {
					itemCount = itemCount + 1; 
				}
			}
	}
	
	/** 
	 * Description: This method is used to create the Car.
	 * @param id The registration number of the Car.
	 * @param make The make of the Car.
	 * @param model The model of the Car.
	 * @param driverName The driver name of the Car.
	 * @param numPassengers The passenger capacity of the Car.
	 * @return A string representing if the Car is successfully created.
	 * @throws Exception The exceptions required.
	 */
	public String createCar(String id, String make, String model, String driverName, int numPassengers) throws Exception {
		String validId = isValidId(id);
		
		if (isValidId(id).contains("Error:")) {
			return validId;
		}
		
		if (!checkIfCarExists(id)) {
			cars[itemCount] = new Car(id, make, model, driverName, numPassengers);
			itemCount++;
			return "New Car added successfully for registion number: " + cars[itemCount-1].getRegistrationNumber();
		}
		
		return "Error: Already exists in the system.";
	}
}
