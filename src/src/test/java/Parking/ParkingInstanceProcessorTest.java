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
	public void setUp(){
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		parkingInstanceArrayList = pip.createParkingInstanceArray(filePath);
	}
	
	/**
	 * Test for accurate generation of parking instance objects in an ArrayList
	 */
	@Test
	public void testParkingInstanceArrayListValues() {	
		assertEquals("2008-05-30T15:56:01,null,null,406958840AD1665FFCD1BE9C29D515B9", parkingInstanceArrayList.get(0).toString());
		assertEquals("2019-06-10T20:52:37,96300C1,CA,6A0393B80DA93E5330B24517B02369CB", parkingInstanceArrayList.get(1).toString());
		assertEquals("2019-07-20T19:37:30,JYZE42,FL,25872BBC7FC3D983060B86C6B2A15FDD", parkingInstanceArrayList.get(2).toString());
		assertEquals("2019-01-27T16:53:55,COUNSL9,CA,6CA0CFC85D9E75C8CE4ADB31F93C1490", parkingInstanceArrayList.get(3).toString());
		assertEquals("2004-08-27T13:52:55,null,null,4EC4C852B49711AECB94727E2B8894A8", parkingInstanceArrayList.get(4).toString());
		assertEquals("2018-09-21T07:25:24,BLDLYGO,CA,9AFE844CF90CD012EE27155DCF719508", parkingInstanceArrayList.get(5).toString());	
	}
	
	/**
	 * Test for accurate size of ArrayList with parking instance objects
	 */
	@Test
	public void testParkingInstanceArrayListSize() {
		assertTrue(parkingInstanceArrayList.size()==6);
	}

}
