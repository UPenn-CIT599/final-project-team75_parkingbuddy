package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

public class LicenseOCRTest {

	/**
	 * Test OpenALPR API for image files without a car. 
	 * API should return a string "null,null" since there is no license plate and state information from the image file.
	 * We test a folder of image files with no cars.  
	 */
	@Test
	public void testAPIForNullCar() {
		LicenseOCR test = new LicenseOCR(); 
		JPEGReader r = new JPEGReader();

		Path filePath = Paths.get("src/test/java/Parking/NullCarFolder/");
		ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
		photoArrayList = r.createPhotos(filePath);
		
		ArrayList<Car> carArrayList = new ArrayList<Car>();
		for (Photo photo : photoArrayList) {
			Car myCar = test.createCar(photo);
			carArrayList.add(myCar);
		}

		assertEquals("null,null", carArrayList.get(0).getLicensePlateAndState());
		assertEquals("null,null", carArrayList.get(1).getLicensePlateAndState());
		assertEquals("null,null", carArrayList.get(2).getLicensePlateAndState());
	}
	
	/**
	 * Test OpenALPR API for image files with a car. 
	 * API should return a string with the car license plate and state information from the image file.
	 * We test a folder of image files with cars.  
	 */
	@Test
	public void testAPIForCar() {
		LicenseOCR test = new LicenseOCR(); 
		JPEGReader r = new JPEGReader();

		Path filePath = Paths.get("src/test/java/Parking/CarFolder/");
		ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
		photoArrayList = r.createPhotos(filePath);
		
		ArrayList<Car> carArrayList = new ArrayList<Car>();
		for (Photo photo : photoArrayList) {
			Car myCar = test.createCar(photo);
			carArrayList.add(myCar);
		}

		assertEquals("96300C1,CA", carArrayList.get(0).getLicensePlateAndState());
		assertEquals("JYZE42,FL", carArrayList.get(1).getLicensePlateAndState());
		assertEquals("COUNSL9,CA", carArrayList.get(2).getLicensePlateAndState());
	}
	

}
