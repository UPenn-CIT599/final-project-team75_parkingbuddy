package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

/*
 * This class tests the JPEGReader class by testing the readDates method specifically, to ensure the
 * LocalDateTime date data is extracted correctly from each image's EXIF metadata.
 */
public class PhotoFactoryTest {
  /**
   * Test for a folder with a single image file. We are testing the readDates method to ensure the
   * LocalDateTime date data is extracted correctly from each image's EXIF metadata.
   */
  @Test
  public void testOneImage() throws ParkingException {
    Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
    ArrayList<Photo> photoArrayList = PhotoFactory.createPhotos(filePath);
    ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
    for (Photo photo : photoArrayList) {
      LocalDateTime date = photo.getDateTime();
      dates.add(date);
    }
    assertEquals(1, dates.size());
    assertEquals(LocalDateTime.of(2004, 8, 27, 13, 52, 55), dates.get(0));
  }


  /**
   * Test for an empty folder
   */
  @Test(expected = ParkingException.class)
  public void testEmptyFolder() throws ParkingException {
    Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
    PhotoFactory.createPhotos(filePath);
  }

  /**
   * Test for a folder with invalid path
   */
  @Test(expected = ParkingException.class)
  public void testInvalidPath() throws ParkingException {
    Path filePath = Paths.get("//invalidPath//");
    PhotoFactory.createPhotos(filePath);
  }


  /**
   * Test for a folder with multiple image files. We are testing the readDates method to ensure the
   * LocalDateTime date data is extracted correctly from each image's EXIF metadata.
   */
  @Test
  public void testMultipleFolder() throws ParkingException {
    Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
    ArrayList<Photo> photos = PhotoFactory.createPhotos(filePath);

    assertEquals(6, photos.size());

    assertEquals(LocalDateTime.of(2008, 05, 30, 15, 56, 01), photos.get(0).getDateTime());
    assertEquals(LocalDateTime.of(2019, 06, 10, 20, 52, 37), photos.get(1).getDateTime());
    assertEquals(LocalDateTime.of(2019, 07, 20, 19, 37, 30), photos.get(2).getDateTime());
    assertEquals(LocalDateTime.of(2019, 01, 27, 16, 53, 55), photos.get(3).getDateTime());
    assertEquals(LocalDateTime.of(2004, 8, 27, 13, 52, 55), photos.get(4).getDateTime());
    assertEquals(LocalDateTime.of(2018, 9, 21, 7, 25, 24), photos.get(5).getDateTime());

    assertEquals("406958840AD1665FFCD1BE9C29D515B9", photos.get(0).getMd5Hash());
    assertEquals("6A0393B80DA93E5330B24517B02369CB", photos.get(1).getMd5Hash());
    assertEquals("25872BBC7FC3D983060B86C6B2A15FDD", photos.get(2).getMd5Hash());
    assertEquals("6CA0CFC85D9E75C8CE4ADB31F93C1490", photos.get(3).getMd5Hash());
    assertEquals("4EC4C852B49711AECB94727E2B8894A8", photos.get(4).getMd5Hash());
    assertEquals("9AFE844CF90CD012EE27155DCF719508", photos.get(5).getMd5Hash());

    assertEquals("src/test/java/Parking/MultipleImagesFolder/Canon_40D.jpg",
        photos.get(0).getPath());
    assertEquals("src/test/java/Parking/MultipleImagesFolder/photo.jpg", photos.get(1).getPath());
    assertEquals("src/test/java/Parking/MultipleImagesFolder/20190720_193730.jpg",
        photos.get(2).getPath());
    assertEquals("src/test/java/Parking/MultipleImagesFolder/photo2.jpg", photos.get(3).getPath());
    assertEquals("src/test/java/Parking/MultipleImagesFolder/Canon_DIGITAL_IXUS_400.jpg",
        photos.get(4).getPath());
    assertEquals("src/test/java/Parking/MultipleImagesFolder/photo3.jpg", photos.get(5).getPath());
  }


  /**
   * Test for a folder with .png image files (which are invalid). We are testing the readDates
   * method to ensure the LocalDateTime date data is extracted correctly from each image's EXIF
   * metadata.
   */
  @Test(expected = ParkingException.class)
  public void testPNGImagesFolder() throws ParkingException {
    Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
    PhotoFactory.createPhotos(filePath);
  }


  /**
   * Test for a folder with mixed image file formats (.jpg and .png). We are testing the readDates
   * method to ensure the LocalDateTime date data is extracted correctly from each image's EXIF
   * metadata.
   */
  @Test
  public void testMixedImagesFolder() throws ParkingException {
    Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
    ArrayList<Photo> photoArrayList = PhotoFactory.createPhotos(filePath);
    ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
    for (Photo photo : photoArrayList) {
      LocalDateTime date = photo.getDateTime();
      dates.add(date);
    }
    assertEquals(2, dates.size());
    assertEquals(LocalDateTime.of(2008, 05, 30, 15, 56, 01), dates.get(0));
  }
}
