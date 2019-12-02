package Parking;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

public class ParkingControllerTest {
	
	private ParkingController pc = new ParkingController();
	private ArrayList<ParkingAggregate> results = new ArrayList<ParkingAggregate>();
	
	/**
	 * Test for violation report to ensure the correct information for the car violations within the 
	 * inputted timeframe is returned.
	 */
	@Test
	public void testViolationReport() {
		results = pc.getParkingAggregates(LocalDate.of(2010, 2, 11),LocalDate.of(2019, 6, 11));
		assertEquals(1, results.get(0).getOvernightCount());
		assertEquals(2, results.get(1).getOvernightCount());
	}
}