package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ParkingInstanceProcessorTest {
	ArrayList<ParkingInstance> parkingInstanceArrayList = new ArrayList<ParkingInstance>();

	@Before
	public void setUp() throws ParkingException {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor(null);
		parkingInstanceArrayList = pip.createParkingInstances(filePath);
	}

	/**
	 * Test for accurate generation of parking instance objects in an ArrayList
	 */
	@Test
	public void testParkingInstanceArrayListValues() {
		assertEquals(4, parkingInstanceArrayList.size());
		assertEquals("CA, 96300C1, 2019-06-10 20:52:37, 6A0393B80DA93E5330B24517B02369CB",
				parkingInstanceArrayList.get(0).toString());
		assertEquals("FL, JYZE42, 2019-07-20 19:37:30, 25872BBC7FC3D983060B86C6B2A15FDD",
				parkingInstanceArrayList.get(1).toString());
		assertEquals("CA, COUNSL9, 2019-01-27 16:53:55, 6CA0CFC85D9E75C8CE4ADB31F93C1490",
				parkingInstanceArrayList.get(2).toString());
		assertEquals("CA, BLDLYGO, 2018-09-21 07:25:24, 9AFE844CF90CD012EE27155DCF719508",
				parkingInstanceArrayList.get(3).toString());
	}
}
