package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;


public class JPEGReaderTest {
	private JPEGReader r = new JPEGReader();
	private ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
	private ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();

	/**
	 * Test for a folder with a single image file.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testReadDatesOneImage() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		photoArrayList = r.createPhotos(filePath);
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2004-08-27T13:52:55]", dates.toString());
	}

	
	/**
	 * Test for an empty folder. 
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testReadEmptyFolder() {
		Path filePath = Paths.get("src/test/java/Parking/EmptyFolder/");
		photoArrayList = r.createPhotos(filePath);
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[]", dates.toString());
	}

	
	/**
	 * Test for a folder with multiple image files.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testReadDatesMultipleFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2008-05-30T15:56:01, 2019-06-10T20:52:37, 2019-07-20T19:37:30, 2019-01-27T16:53:55, 2004-08-27T13:52:55, 2018-09-21T07:25:24]", dates.toString());
	}

	
	/**
	 * Test for a folder with .png image files (which are invalid). 
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testReadDatesPNGImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[]", dates.toString());
	}

	
	/**
	 * Test for a folder with mixed image file formats (.jpg and .png).
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testReadDatesMixedImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		for (Photo photo : photoArrayList) {
			LocalDateTime date = photo.getCreationDate();
			dates.add(date);
		}
		assertEquals("[2008-05-30T15:56:01, 2004-08-27T13:52:55]", dates.toString());
	}
	
	
	/**
	 * Test for creation of photo objects to ensure the correct values are set to the object variables.
	 */
	@Test
	public void testCreatePhoto() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertEquals("2008-05-30T15:56:01,406958840AD1665FFCD1BE9C29D515B9,src/test/java/Parking/MultipleImagesFolder/Canon_40D.jpg", photoArrayList.get(0).getPhotoToString());
		assertEquals("2019-06-10T20:52:37,6A0393B80DA93E5330B24517B02369CB,src/test/java/Parking/MultipleImagesFolder/photo.jpg", photoArrayList.get(1).getPhotoToString());
		assertEquals("2019-07-20T19:37:30,25872BBC7FC3D983060B86C6B2A15FDD,src/test/java/Parking/MultipleImagesFolder/20190720_193730.jpg", photoArrayList.get(2).getPhotoToString());
		assertEquals("2019-01-27T16:53:55,6CA0CFC85D9E75C8CE4ADB31F93C1490,src/test/java/Parking/MultipleImagesFolder/photo2.jpg", photoArrayList.get(3).getPhotoToString());
		assertEquals("2004-08-27T13:52:55,4EC4C852B49711AECB94727E2B8894A8,src/test/java/Parking/MultipleImagesFolder/Canon_DIGITAL_IXUS_400.jpg", photoArrayList.get(4).getPhotoToString());
		assertEquals("2018-09-21T07:25:24,9AFE844CF90CD012EE27155DCF719508,src/test/java/Parking/MultipleImagesFolder/photo3.jpg", photoArrayList.get(5).getPhotoToString());
	}
	
	
}
