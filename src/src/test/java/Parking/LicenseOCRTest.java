package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

public class LicenseOCRTest {
	
	private LicenseOCR test = new LicenseOCR(); 
	private JPEGReader r = new JPEGReader();
	private ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
	private ArrayList<Car> carArrayList = new ArrayList<Car>();
	
	/**
	 * Test OpenALPR API for image files without a car. 
	 * API should return a string "null,null" since there is no license plate and state information from the image file.
	 * We test a folder of image files with no cars.  
	 */
	@Test
	public void testAPIForNullCar() {
		Path filePath = Paths.get("src/test/java/Parking/NullCarFolder/");
		photoArrayList = r.createPhotos(filePath);
		
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
		Path filePath = Paths.get("src/test/java/Parking/CarFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		for (Photo photo : photoArrayList) {
			Car myCar = test.createCar(photo);
			carArrayList.add(myCar);
		}

		assertEquals("96300C1,CA", carArrayList.get(0).getLicensePlateAndState());
		assertEquals("JYZE42,FL", carArrayList.get(1).getLicensePlateAndState());
		assertEquals("COUNSL9,CA", carArrayList.get(2).getLicensePlateAndState());
	}
	

}
