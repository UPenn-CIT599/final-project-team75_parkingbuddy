package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ParkingInstanceProcessorTest {
	
	private ArrayList<ParkingInstance> parkingInstanceArrayList = new ArrayList<ParkingInstance>();
	
	@Before
	public void setUp() throws PhotoException {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		parkingInstanceArrayList = pip.createParkingInstances(filePath);
	}
	
	/**
	 * Test for accurate generation of parking instance objects in an ArrayList
	 */
	@Test
	public void testParkingInstanceArrayListValues() {	
		assertEquals(4, parkingInstanceArrayList.size());
		assertEquals("CA, 96300C1, 2019-06-10 20:52:37, 6a0393b80da93e5330b24517b02369cb", parkingInstanceArrayList.get(0).toString());
		assertEquals("FL, JYZE42, 2019-07-20 19:37:30, 25872bbc7fc3d983060b86c6b2a15fdd", parkingInstanceArrayList.get(1).toString());
		assertEquals("CA, COUNSL9, 2019-01-27 16:53:55, 6ca0cfc85d9e75c8ce4adb31f93c1490", parkingInstanceArrayList.get(2).toString());
		assertEquals("CA, BLDLYGO, 2018-09-21 07:25:24, 9afe844cf90cd012ee27155dcf719508", parkingInstanceArrayList.get(3).toString());
	}
}
