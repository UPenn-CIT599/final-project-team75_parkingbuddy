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
public class PhotoFactoryTest {
	/**
	 * Test for a folder with a single image file.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testOneImage() throws PhotoException {
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
	@Test(expected=PhotoException.class)
	public void testEmptyFolder() throws PhotoException {
		Path filePath = Paths.get("/src/test/java/Parking/EmptyFolder/");
		PhotoFactory.createPhotos(filePath);
	}

	/**
	 * Test for a folder with invalid path
	 */
	@Test(expected=PhotoException.class)
	public void testInvalidPath() throws PhotoException {
		Path filePath = Paths.get("//invalidPath//");
		PhotoFactory.createPhotos(filePath);
	}

	
	/**
	 * Test for a folder with multiple image files.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testMultipleFolder() throws PhotoException {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<Photo> photoArrayList = PhotoFactory.createPhotos(filePath);
		ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getDateTime();
			dates.add(date);
		}
		assertEquals(6, dates.size());
		assertEquals(LocalDateTime.of(2008, 05, 30, 15, 56, 01), dates.get(0));
		assertEquals(LocalDateTime.of(2019, 06, 10, 20, 52, 37), dates.get(1));
		assertEquals(LocalDateTime.of(2019, 07, 20, 19, 37, 30), dates.get(2));
		assertEquals(LocalDateTime.of(2019, 01, 27, 16, 53, 55), dates.get(3));
		assertEquals(LocalDateTime.of(2004, 8, 27, 13, 52, 55), dates.get(4));
		assertEquals(LocalDateTime.of(2018, 9, 21, 7, 25, 24), dates.get(5));
	}

	
	/**
	 * Test for a folder with .png image files (which are invalid). 
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test(expected=PhotoException.class)
	public void testPNGImagesFolder() throws PhotoException {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		PhotoFactory.createPhotos(filePath);
	}

	
	/**
	 * Test for a folder with mixed image file formats (.jpg and .png).
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testMixedImagesFolder() throws PhotoException {
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
