package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import cars.Car;
import cars.SilverServiceCar;
import exceptions.CorruptedFileException;

/**
 * Description:	This class saves and loads the cars in the application.
 * <br>
 * Class: Persistence
 * @author Darren Huang
 * @version 1.0
 * @since 24/05/2019
 */

public class Persistence {
	
	/**
	 * Description: This method saves cars when the user exits.
	 * @param cars The cars in the array.
	 * @throws IOException The exception required.
	 */
	public void saveCar(Car[] cars) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("carsavefile.txt")));
		
		for (int i = 0; i < cars.length; i = i + 1) {
			
			if (cars[i] != null) {
				
				if (cars[i].getClass().equals(Car.class)) {
					String string = cars[i].getRegistrationNumber() + ":" +
									cars[i].getMake() + ":" +
									cars[i].getModel() + ":" +
									cars[i].getDriverName() + ":" +
									cars[i].getPassengerCapacity();
					pw.println(string);
				}
				
				else if (cars[i] instanceof SilverServiceCar) {
					SilverServiceCar ssCars = (SilverServiceCar) cars[i];
					String string = cars[i].getRegistrationNumber() + ":" +
							cars[i].getMake() + ":" +
							cars[i].getModel() + ":" +
							cars[i].getDriverName() + ":" +
							cars[i].getPassengerCapacity() + ":" + 
							ssCars.getBookingFee() + ":" +
							ssCars.getRefreshmentsForSaveFile();
			pw.println(string);
				}
			}
		}
		
		pw.close();
		
		PrintWriter pwBackup = new PrintWriter(new BufferedWriter(new FileWriter("backupcarsavefile.txt")));
		
		for (int i = 0; i < cars.length; i = i + 1) {
			
			if (cars[i] != null) {
				
				if (cars[i].getClass().equals(Car.class)) {
					String string = cars[i].getRegistrationNumber() + ":" +
									cars[i].getMake() + ":" +
									cars[i].getModel() + ":" +
									cars[i].getDriverName() + ":" +
									cars[i].getPassengerCapacity();
					pwBackup.println(string);
				}
				
				else if (cars[i] instanceof SilverServiceCar) {
					SilverServiceCar ssCars = (SilverServiceCar) cars[i];
					String string = cars[i].getRegistrationNumber() + ":" +
							cars[i].getMake() + ":" +
							cars[i].getModel() + ":" +
							cars[i].getDriverName() + ":" +
							cars[i].getPassengerCapacity() + ":" + 
							ssCars.getBookingFee() + ":" +
							ssCars.getRefreshmentsForSaveFile();
					pwBackup.println(string);
				}
			}
		}
		
		pwBackup.close();
	}
	
	/**
	 * Description: This method saves cars when the user exits.
	 * @param fileName The name of the textfile.
	 * @return An array filled with cars and their details.
	 * @throws Exception The exceptions required.
	 */
	public Car[] readData(String fileName) throws Exception {
		// Create File Reading Objects
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		
		// Create collection of cars to return
		Car[] records = new Car[15];
		
		int i = 0;
		
		while ((line = br.readLine()) != null) {
			// Tokenize String
			StringTokenizer inReader = new StringTokenizer(line, ":");
			
			// Check each line of file for integrity
			checkFileIntegrity(inReader);
			
			// Create car from file
			if (inReader.countTokens() == 5) {
				Car car = createCar(inReader);
				records[i] = car;
				i++;
			}
			
			else if (inReader.countTokens() == 7) {
				Car sscar = createSSCar(inReader);
				records[i] = sscar;
				i++;
			}
//			Car car  = createCar(inReader);
			
			// Add car to collection
//			records[i] = car;
//			i++;
		}
		
		br.close();
		return records;
	}
	
	
	/**
	 * Description: This method checks if the text file is corrupted the number of variables saved in the text file.
	 * @param inReader The line extracted.
	 * @throws Exception The exceptions required.
	 */
	private void checkFileIntegrity(StringTokenizer inReader) throws Exception {
		boolean checkerCar, checkerSSCar;
		checkerCar = true;
		checkerSSCar = true;
		
		//Checks every line to see if it is either a Car or a Silver Service Car.
		//A Car has 5 countTokens while a Silver Service Car has 7 countTokens.
		if (inReader.countTokens() != 5) {
			checkerCar = false;
		}

		if (inReader.countTokens() != 7) {
			checkerSSCar = false;
		}
		
		if (!checkerCar) {
			if (!checkerSSCar) {
				throw new CorruptedFileException("Data was corrupt, please contact the adminstrator.");
			}
		}
	}
	
	/**
	 * Description: Writes a Car to the Car class.
	 * @param inReader The line extracted.
	 * @return A Car is created.
	 * @throws Exception The exceptions required.
	 */
	private Car createCar(StringTokenizer inReader) throws Exception {
		String regNo = inReader.nextToken();
		String make = inReader.nextToken();
		String model = inReader.nextToken();
		String driverName = inReader.nextToken();
		int passengerCapacity = Integer.parseInt(inReader.nextToken());
		Car car = new Car(regNo, make, model, driverName, passengerCapacity);
		return car;
	}
	
	/**
	 * Description: Writes a Silver Service Car to the Silver Service Car class.
	 * @param inReader The line extracted.
	 * @return A Silver Service Car is created.
	 * @throws Exception The exceptions required.
	 */
	private Car createSSCar(StringTokenizer inReader) throws Exception {
		String regNo = inReader.nextToken();
		String make = inReader.nextToken();
		String model = inReader.nextToken();
		String driverName = inReader.nextToken();
		int passengerCapacity = Integer.parseInt(inReader.nextToken());
		double bookingFee = Double.parseDouble(inReader.nextToken());
		String[] refreshments = inReader.nextToken().split(",");
		SilverServiceCar sscar = new SilverServiceCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
		return sscar;
	}	
}
