package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

/*
 * This class tests the JPEGReader class by testing the readDates method specifically, to ensure
 * the LocalDateTime date data is extracted correctly from each image's EXIF metadata.
 */
public class JPEGReaderTest {
	JPEGReader r = new JPEGReader();

	/**
	 * Test for a folder with a single image file
	 */
	@Test
	public void testOneImage() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2004-08-27T13:52:55]", dates.toString());
	}

	/**
	 * Test for an empty folder
	 */
	@Test
	public void testEmptyFolder() {
		Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[]", dates.toString());
	}

	/**
	 * Test for a folder with invalid path
	 */
	@Test
	public void testInvalidPath() {
		Path filePath = Paths.get("//invalidPath//");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[]", dates.toString());
	}

	/**
	 * Test for a folder with multiple image files
	 */
	@Test
	public void testMultipleFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2008-05-30T15:56:01, 2019-06-10T20:52:37, 2019-01-27T16:53:55, 2004-08-27T13:52:55, 2018-09-21T07:25:24]", dates.toString());
	}

	/**
	 * Test for a folder with .png image files (which are invalid)
	 */
	@Test
	public void testPNGImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[]", dates.toString());
	}

	/**
	 * Test for a folder with mixed image file formats (.jpg and .png)
	 */
	@Test
	public void testMixedImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
		ArrayList<Photo> photoArrayList = r.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2008-05-30T15:56:01, 2004-08-27T13:52:55]", dates.toString());
	}
}
