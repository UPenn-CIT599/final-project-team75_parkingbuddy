package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

public class JPEGReaderTest {
	JPEGReader r = new JPEGReader();

	@Test
	public void testOneImageFolder() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		assertEquals("[2004-08-27]",dates.toString()); 
	}
	
	@Test
	public void testEmptyFolder() {
		Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString()); 
	}
	
	@Test
	public void testMultipleImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		assertEquals("[2008-05-30, 2004-08-27]", dates.toString()); 
	}
	
	@Test
	public void testPNGImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString()); 
	}
	
	@Test
	public void testMixedImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		assertEquals("[2008-05-30, 2004-08-27]", dates.toString()); 
	}
}
