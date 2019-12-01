package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class LicenseOCRTest {
	
	private LicenseOCR test = new LicenseOCR(); 
	private JPEGReader r = new JPEGReader();
	private ArrayList<Car> nullCarsCarArrayList = new ArrayList<Car>();
	private ArrayList<Car> carsCarArrayList = new ArrayList<Car>();
	
	@Before
	public void setUp(){
		// Set up for test for photos with no cars
		Path nullCarsFolderPath = Paths.get("src/test/java/Parking/NullCarFolder/");
		ArrayList<Photo> nullCarsPhotoArrayList = new ArrayList<Photo>();
		nullCarsPhotoArrayList = r.createPhotos(nullCarsFolderPath);
		for (Photo photo : nullCarsPhotoArrayList) {
			Car myCar = test.createCar(photo);
			nullCarsCarArrayList.add(myCar);
		}
		
		// Set up for test for photos with cars
		Path carsFolderPath = Paths.get("src/test/java/Parking/CarFolder/");
		ArrayList<Photo> carsPhotoArrayList = new ArrayList<Photo>();
		carsPhotoArrayList = r.createPhotos(carsFolderPath);
		for (Photo photo : carsPhotoArrayList) {
			Car myCar = test.createCar(photo);
			carsCarArrayList.add(myCar);
		}
		
	}
	
	/**
	 * Test OpenALPR API for extraction of license plate information from image files without a car. 
	 * API should return null since there is no license plate information from the image file.
	 * We test a folder of image files with no cars.  
	 */
	@Test
	public void testAPIForNullCarLicensePlate() {
	
		assertNull(nullCarsCarArrayList.get(0).getLicense());
		assertNull(nullCarsCarArrayList.get(1).getLicense());
		assertNull(nullCarsCarArrayList.get(2).getLicense());
	}
	
	/**
	 * Test OpenALPR API for extraction of car state information from image files without a car. 
	 * API should return null since there is no state information from the image file.
	 * We test a folder of image files with no cars.  
	 */
	@Test
	public void testAPIForNullCarState() {
		assertNull(nullCarsCarArrayList.get(0).getState());
		assertNull(nullCarsCarArrayList.get(1).getState());
		assertNull(nullCarsCarArrayList.get(2).getState());
	}
	
	/**
	 * Test OpenALPR API for extraction of license plate information from image files with a car. 
	 * API should return a string with the car license plate number from the image file.
	 * We test a folder of image files with cars.  
	 */
	@Test
	public void testAPIForCarLicensePlate() {
		assertEquals("96300C1", carsCarArrayList.get(0).getLicense());
		assertEquals("JYZE42", carsCarArrayList.get(1).getLicense());
		assertEquals("COUNSL9", carsCarArrayList.get(2).getLicense());
	}
	
	/**
	 * Test OpenALPR API for extraction of car state information from image files with a car. 
	 * API should return a string with the car state from the image file.
	 * We test a folder of image files with cars.  
	 */
	@Test
	public void testAPIForCarState() {
		assertEquals("CA", carsCarArrayList.get(0).getState());
		assertEquals("FL", carsCarArrayList.get(1).getState());
		assertEquals("CA", carsCarArrayList.get(2).getState());
	}
	

}
