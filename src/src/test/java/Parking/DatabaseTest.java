package Parking;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.Test;

public class DatabaseTest extends FakeDatabase {
  @Test
  public void testInsert() throws ParkingException {
    ArrayList<ParkingInstance> parkingInstances = db.getParkingInstancesbyDate(
        new Car("PA", "7XYA125"), LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
    assertEquals(3, parkingInstances.size());
    assertEquals(parking2, parkingInstances.get(0));
    assertEquals(parking3, parkingInstances.get(1));
    assertEquals(parking4, parkingInstances.get(2));
  }

  @Test
  public void testInsertDuplicate() throws ParkingException {
    db.insertParkingInstance(parking1);

    ArrayList<ParkingInstance> parkingInstances = db.getParkingInstancesbyDate(
        new Car("PA", "7XYA125"), LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
    assertEquals(3, parkingInstances.size());
    assertEquals(parking2, parkingInstances.get(0));
    assertEquals(parking3, parkingInstances.get(1));
    assertEquals(parking4, parkingInstances.get(2));
  }

  @Test(expected = ParkingException.class)
  public void testInsertInvalidCar() throws ParkingException {
    ParkingInstance parkingInvalid =
        new ParkingInstance(new Car("", ""), new Photo(parking1.getPhoto().getImage(),
            LocalDateTime.of(2018, 9, 14, 03, 56, 12), "Y", "/path/to/photo"));
    db.insertParkingInstance(parkingInvalid);

    ArrayList<ParkingInstance> parkingInstances = db.getParkingInstancesbyDate(
        new Car("PA", "7XYA125"), LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
    assertEquals(3, parkingInstances.size());
    assertEquals(parking2, parkingInstances.get(0));
    assertEquals(parking3, parkingInstances.get(1));
    assertEquals(parking4, parkingInstances.get(2));
  }

  @Test(expected = ParkingException.class)
  public void testInsertInvalidPhotoHash() throws ParkingException {
    ParkingInstance parkingInvalid =
        new ParkingInstance(new Car("PA", "7XYA125"), new Photo(parking1.getPhoto().getImage(),
            LocalDateTime.of(2018, 9, 14, 03, 56, 12), "", "/path/to/photo"));
    db.insertParkingInstance(parkingInvalid);

    ArrayList<ParkingInstance> parkingInstances = db.getParkingInstancesbyDate(
        new Car("PA", "7XYA125"), LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
    assertEquals(3, parkingInstances.size());
    assertEquals(parking2, parkingInstances.get(0));
    assertEquals(parking3, parkingInstances.get(1));
    assertEquals(parking4, parkingInstances.get(2));
  }

  @Test
  public void testAggregate() throws ParkingException {
    ArrayList<ParkingAggregate> parkings =
        db.getParkingAggregates(LocalDate.of(2010, 2, 11), LocalDate.of(2019, 12, 31));
    assertEquals(2, parkings.size());
    assertEquals(new Car("PA", "7XYA125"), parkings.get(0).getCar());
    assertEquals(2, parkings.get(0).getOvernightCount());
    assertEquals(new Car("PA", "7XYA124"), parkings.get(1).getCar());
    assertEquals(1, parkings.get(1).getOvernightCount());
  }
}
