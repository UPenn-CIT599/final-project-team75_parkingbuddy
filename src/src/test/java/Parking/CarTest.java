package Parking;

import static org.junit.Assert.*; 

import org.junit.Test;

public class CarTest {
	
	/**
	 * Test whether the constructor works well taking in the variables,
	 * even with empty parameters.
	 */
	@Test
	public void TestCarConstructor() {
		Car newCar1 = new Car("CA", "1234567");
		Car newCar2 = new Car("", "1234567");
		Car newCar3 = new Car("PA", "");
		System.out.println(newCar1.toString());
		
		assertEquals("CA1234567", newCar1.getState() + newCar1.getLicense());
		assertEquals("1234567", newCar2.getState() + newCar2.getLicense());
		assertEquals("PA", newCar3.getState() + newCar3.getLicense());
	}

}
