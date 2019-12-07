package Parking;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarTest {
  Car newCar1 = new Car("CA", "1234567");
  Car newCar2 = new Car("", "1234567");
  Car newCar3 = new Car("PA", "");
  Car newCar4 = new Car("CA", "1234567");

  /**
   * Test whether the constructor works well taking in the variables, even with empty parameters.
   */
  @Test
  public void TestConstandGetters() {

    assertEquals("CA1234567", newCar1.getState() + newCar1.getLicense());
    assertEquals("1234567", newCar2.getState() + newCar2.getLicense());
    assertEquals("PA", newCar3.getState() + newCar3.getLicense());
  }

  /**
   * Test the toString method
   */
  @Test
  public void TesttoString() {

    assertEquals("CA, 1234567", newCar1.toString());
  }

  /**
   * Test the equals method that compares two car objects
   */
  @Test
  public void TestEquals() {

    assertTrue(newCar1.equals(newCar4));
    assertFalse(newCar1.equals(newCar2));
  }
}
