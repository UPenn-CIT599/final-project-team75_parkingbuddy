package Parking;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

public class ParkingControllerTest {

	private ParkingController pc = ParkingController.getInstance();
	private ArrayList<ParkingAggregate> results = new ArrayList<ParkingAggregate>();

	/**
	 * Test for violation report to ensure the correct information for the car violations within the
	 * Inputed time frame is returned.
	 */
	@Test
	public void testViolationReport() {
		results = pc.getParkingAggregates(LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
		assertEquals(1, results.get(0).getOvernightCount());
		assertEquals(1, results.get(1).getOvernightCount());
		assertEquals(1, results.get(2).getOvernightCount());
		assertEquals(1, results.get(3).getOvernightCount());
		assertEquals(1, results.get(4).getOvernightCount());
		assertEquals(1, results.get(5).getOvernightCount());
		assertEquals(1, results.get(6).getOvernightCount());
		assertEquals(1, results.get(7).getOvernightCount());
		assertEquals(1, results.get(8).getOvernightCount());
		assertEquals(3, results.get(9).getOvernightCount());
		assertEquals(2, results.get(10).getOvernightCount());
		assertEquals(1, results.get(11).getOvernightCount());
		assertEquals(1, results.get(12).getOvernightCount());
		assertEquals(2, results.get(13).getOvernightCount());
		
	}
}
