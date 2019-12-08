package Parking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * This is a fake database that will be used to test the classes.
 */
public class FakeDatabase {
  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  protected Database db = null;

  protected ParkingInstance parking1;
  protected ParkingInstance parking2;
  protected ParkingInstance parking3;
  protected ParkingInstance parking4;

  @Before
  public void setUp() throws IOException, ParkingException {
    File dbFile = tmpFolder.newFile("test.db");
    db = new Database(dbFile.toPath());

    Path filePath = Paths.get("src/photos/photo.jpg");
    Photo photo = PhotoFactory.createPhoto(filePath.toFile());
    parking1 = new ParkingInstance(new Car("PA", "7XYA124"), new Photo(photo.getImage(),
        LocalDateTime.of(2018, 8, 13, 20, 56, 12), "A", "/path/to/photo"));

    parking2 = new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
        LocalDateTime.of(2018, 8, 13, 21, 56, 12), "B", "/path/to/photo"));

    parking3 = new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
        LocalDateTime.of(2018, 9, 13, 21, 57, 12), "C", "/path/to/photo"));

    parking4 = new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
        LocalDateTime.of(2018, 9, 14, 03, 56, 12), "D", "/path/to/photo"));

    db.insertParkingInstance(parking1);
    db.insertParkingInstance(parking2);
    db.insertParkingInstance(parking3);
    db.insertParkingInstance(parking4);
  }
}
