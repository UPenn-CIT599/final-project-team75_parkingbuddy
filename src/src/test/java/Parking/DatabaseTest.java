package Parking;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DatabaseTest {
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    Database db = null;

    ParkingInstance parking1;
    ParkingInstance parking2;
    ParkingInstance parking3;
    ParkingInstance parking4;

    @Before
    public void setUp() throws IOException, PhotoException {
        File dbFile = tmpFolder.newFile("test.db");
        db = new Database(dbFile.toPath());

        Path filePath = Paths.get("src/photos/photo.jpg");
        Photo photo = PhotoFactory.createPhoto(filePath.toFile());
        parking1 = new ParkingInstance(new Car("PA", "7XYA124"),
                new Photo(photo.getImage(),
                        LocalDateTime.of(2018, 8, 13, 20, 56, 12), "A",
                        "/path/to/photo"));

        parking2 = new ParkingInstance(new Car("PA", "7XYA125"),
                new Photo(photo.getImage(),
                        LocalDateTime.of(2018, 8, 13, 21, 56, 12), "B",
                        "/path/to/photo"));

        parking3 = new ParkingInstance(new Car("PA", "7XYA125"),
                new Photo(photo.getImage(),
                        LocalDateTime.of(2018, 9, 13, 21, 57, 12), "C",
                        "/path/to/photo"));

        parking4 = new ParkingInstance(new Car("PA", "7XYA125"),
                new Photo(photo.getImage(),
                        LocalDateTime.of(2018, 9, 14, 03, 56, 12), "D",
                        "/path/to/photo"));

        db.insertParkingInstance(parking1);
        db.insertParkingInstance(parking2);
        db.insertParkingInstance(parking3);
        db.insertParkingInstance(parking4);
    }

    @Test
    public void testInsert() throws PhotoException {
        ArrayList<ParkingInstance> parkingInstances =
                db.getParkingInstancesbyDate(new Car("PA", "7XYA125"),
                        LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
        assertEquals(3, parkingInstances.size());
        assertEquals(parking2, parkingInstances.get(0));
        assertEquals(parking3, parkingInstances.get(1));
        assertEquals(parking4, parkingInstances.get(2));
    }

    @Test
    public void testAggregate() throws PhotoException {
        ArrayList<ParkingAggregate> parkings = db.getAggregatedParkingInstances(LocalDate.of(2010, 2, 11),
                LocalDate.of(2019, 6, 11));
        assertEquals(2, parkings.size());
        assertEquals(new Car("PA", "7XYA124"), parkings.get(0).getCar());
        assertEquals(1, parkings.get(0).getOvernightCount());
        assertEquals(new Car("PA", "7XYA125"), parkings.get(1).getCar());
        assertEquals(2, parkings.get(1).getOvernightCount());
    }
}
