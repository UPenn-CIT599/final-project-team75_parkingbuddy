package Parking;

import static org.junit.Assert.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class ParkingControllerTest extends FakeDatabase {
	ParkingController pc;
	ArrayList<ParkingAggregate> results = new ArrayList<ParkingAggregate>();

	@Before
	public void setUp() throws IOException, ParkingException {
		super.setUp();
		pc = new ParkingController(db);
	}

	/**
	 * Test for violation report to ensure the correct information for the car violations within the
	 * Inputed time frame is returned.
	 */
	@Test
	public void testViolationReport() {
		ArrayList<ParkingAggregate> parkings =
				pc.getParkingAggregates(LocalDate.of(2010, 2, 11), LocalDate.of(2019, 12, 31));
		assertEquals(2, parkings.size());
		assertEquals(new Car("PA", "7XYA125"), parkings.get(0).getCar());
		assertEquals(2, parkings.get(0).getOvernightCount());
		assertEquals(new Car("PA", "7XYA124"), parkings.get(1).getCar());
		assertEquals(1, parkings.get(1).getOvernightCount());

	}
}
