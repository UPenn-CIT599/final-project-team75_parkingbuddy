package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

public class JPEGReaderTest {
	JPEGReader r = new JPEGReader();

	@Test
	public void testOneImage() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[Fri Aug 27 06:52:55 PDT 2004]",dates.toString()); 
	}
	
	@Test
	public void testEmptyFolder() {
		Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString()); 
	}
	
	@Test
	public void testMultipleFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[Fri May 30 08:56:01 PDT 2008, Fri Aug 27 06:52:55 PDT 2004]", dates.toString()); 
	}
	
	@Test
	public void testPngFolder() {
		Path filePath = Paths.get("/Users/minschoi/Downloads/pngFolder/");
		ArrayList<LocalDateTime> dates = r.readDates(filePath);
		assertEquals("[]", dates.toString()); 
	}
}
