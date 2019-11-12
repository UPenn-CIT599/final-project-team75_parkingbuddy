package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

/*
 * This class tests the JPEGReader class by testing various cases to make
 * sure that the class and its method of reading JPEG files is valid.
 */
public class JPEGReaderTest {
	JPEGReader r = new JPEGReader();

	@Test
	public void testOneImage() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[2004-08-27T13:52:55]", dates.toString());
	}

	@Test
	public void testEmptyFolder() {
		Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString());
	}

	@Test
	public void testInvalidPath() {
		Path filePath = Paths.get("//");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString());
	}

	@Test
	public void testMultipleFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[2008-05-30T15:56:01, 2004-08-27T13:52:55]", dates.toString());
	}

	@Test
	public void testPNGImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString());
	}

	@Test
	public void testMixedImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[2008-05-30T15:56:01, 2004-08-27T13:52:55]", dates.toString());
	}
}
