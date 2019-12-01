package Parking;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.junit.Test;


public class JPEGReaderTest {
	private JPEGReader r = new JPEGReader();
	private ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
	
	/**
	 * Test for a folder with a single image file.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testOneImage() {
		Path filePath = Paths.get("src/test/java/Parking/OneImageFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertTrue(photoArrayList.size()==1);
	}

	
	/**
	 * Test for an empty folder. 
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testEmptyFolder() {
		Path filePath = Paths.get("src/test/java/Parking/EmptyFolder/");
		photoArrayList = r.createPhotos(filePath);

		assertTrue(photoArrayList.size()==0);
	}

	
	/**
	 * Test for a folder with multiple image files.
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testMultipleFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertTrue(photoArrayList.size()==6);
	}

	
	/**
	 * Test for a folder with .png image files (which are invalid). 
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testPNGImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/PNGImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertTrue(photoArrayList.size()==0);
	}

	
	/**
	 * Test for a folder with mixed image file formats (.jpg and .png).
	 * We are testing the readDates method to ensure the LocalDateTime date data 
	 * is extracted correctly from each image's EXIF metadata.
	 */
	@Test
	public void testMixedImagesFolder() {
		Path filePath = Paths.get("src/test/java/Parking/MixedImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertTrue(photoArrayList.size()==2);
	}
	
	
	/**
	 * Test for LocalDateTime extracted from image EXIF metadata.
	 */
	@Test
	public void testReadDatesFromPhotos() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertEquals(LocalDateTime.of(2008, Month.MAY, 30, 15, 56, 01), photoArrayList.get(0).getCreationDate());
		assertEquals(LocalDateTime.of(2019, Month.JUNE, 10, 20, 52, 37), photoArrayList.get(1).getCreationDate());
		assertEquals(LocalDateTime.of(2019, Month.JULY, 20, 19, 37, 30), photoArrayList.get(2).getCreationDate());
		assertEquals(LocalDateTime.of(2019, Month.JANUARY, 27, 16, 53, 55), photoArrayList.get(3).getCreationDate());
		assertEquals(LocalDateTime.of(2004, Month.AUGUST, 27, 13, 52, 55), photoArrayList.get(4).getCreationDate());
		assertEquals(LocalDateTime.of(2018, Month.SEPTEMBER, 21, 07, 25, 24), photoArrayList.get(5).getCreationDate());
	}
	
	/**
	 * Test for md5 hash string generated for each photo.
	 */
	@Test
	public void testGetMD5FromPhotos() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertEquals("406958840AD1665FFCD1BE9C29D515B9",photoArrayList.get(0).getPhotoHash());
		assertEquals("6A0393B80DA93E5330B24517B02369CB",photoArrayList.get(1).getPhotoHash());
		assertEquals("25872BBC7FC3D983060B86C6B2A15FDD",photoArrayList.get(2).getPhotoHash());
		assertEquals("6CA0CFC85D9E75C8CE4ADB31F93C1490",photoArrayList.get(3).getPhotoHash());
		assertEquals("4EC4C852B49711AECB94727E2B8894A8",photoArrayList.get(4).getPhotoHash());
		assertEquals("9AFE844CF90CD012EE27155DCF719508",photoArrayList.get(5).getPhotoHash());
	}
	
	
	/**
	 * Test for file path generated from each image file.
	 */
	@Test
	public void testFilePathFromPhotos() {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		photoArrayList = r.createPhotos(filePath);
		
		assertEquals("src/test/java/Parking/MultipleImagesFolder/Canon_40D.jpg",photoArrayList.get(0).getPhotoFilePath());
		assertEquals("src/test/java/Parking/MultipleImagesFolder/photo.jpg",photoArrayList.get(1).getPhotoFilePath());
		assertEquals("src/test/java/Parking/MultipleImagesFolder/20190720_193730.jpg",photoArrayList.get(2).getPhotoFilePath());
		assertEquals("src/test/java/Parking/MultipleImagesFolder/photo2.jpg",photoArrayList.get(3).getPhotoFilePath());
		assertEquals("src/test/java/Parking/MultipleImagesFolder/Canon_DIGITAL_IXUS_400.jpg",photoArrayList.get(4).getPhotoFilePath());
		assertEquals("src/test/java/Parking/MultipleImagesFolder/photo3.jpg",photoArrayList.get(5).getPhotoFilePath());
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
