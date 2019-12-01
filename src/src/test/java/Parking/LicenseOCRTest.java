package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

public class LicenseOCRTest {
	private LicenseOCR test = new LicenseOCR(); 
	private ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
	private ArrayList<Car> carArrayList = new ArrayList<Car>();
	
	/**
	 * Test OpenALPR API for image files without a car. 
	 * API should return a string "null,null" since there is no license plate and state information from the image file.
	 * We test a folder of image files with no cars.  
	 */
	@Test
	public void testAPIForNullCar() throws PhotoException {		
		for (Photo photo : photoArrayList) {
			Car myCar = test.getCarWithOpenALPR(photo);
			carArrayList.add(myCar);
		}

		assertEquals(0, carArrayList.size());
	}
	
	/**
	 * Test OpenALPR API for image files with a car. 
	 * API should return a string with the car license plate and state information from the image file.
	 * We test a folder of image files with cars.  
	 */
	@Test
	public void testAPIForCar() throws PhotoException {
		Path filePath = Paths.get("src/test/java/Parking/CarFolder/");
		photoArrayList = PhotoFactory.createPhotos(filePath);
		
		for (Photo photo : photoArrayList) {
			Car myCar = test.getCarWithOpenALPR(photo);
			carArrayList.add(myCar);
		}

		assertEquals("CA", carArrayList.get(0).getState());
		assertEquals("96300C1", carArrayList.get(0).getLicense());
		assertEquals("FL", carArrayList.get(1).getState());
		assertEquals("JYZE42", carArrayList.get(1).getLicense());
		assertEquals("CA", carArrayList.get(2).getState());
		assertEquals("COUNSL9", carArrayList.get(2).getLicense());
	}
	

}
