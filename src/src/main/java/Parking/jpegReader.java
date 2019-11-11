package Parking;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.drew.imaging.*;
import com.drew.metadata.*;
import com.drew.metadata.exif.*;

/**
 * This class reads image files and stores the information of multiple files.
 * 
 * @author minschoi
 *
 */
public class JPEGReader {

	/**
	 * This method extracts the original date of the files from a folder by
	 * extracting exif data and storing them in an ArrayList of String.
	 * 
	 * @param path
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> readDates(Path path) {
		// initializing ArrayList to return
		ArrayList<String> dates = new ArrayList<String>();

		try {
			// store files from the folder location
			File[] files = path.toFile().listFiles();

			// iterate through each file in the folder
			try {
				for (int i = 0; i < files.length; i++) {
					try {
						// get metadata from the file and obtain Exif date and time
						Metadata metadata = ImageMetadataReader.readMetadata(files[i]);
						ExifSubIFDDirectory dir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
						Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
						
						// add to the ArrayList;
						dates.add(date.toString());
					// various cases for error
					} catch (NullPointerException e) {
						System.out.println("File " + files[i].toString() + " doesn't have metadata.");
					} catch (IOException e) {
						System.out.println("File \"" + files[i].toString() + "\" cannot read metadata.");
					} catch (ImageProcessingException e) {
						System.out.println("File \"" + files[i].toString() + "\" format could not be determined.");
					}
				}
			} catch (NullPointerException e) {
				System.out.println("The folder is empty");
			}
		} catch (ProviderNotFoundException e) {
			System.out.println("Given path is invalid");
		}

		return dates;
	}

	/**
	 * photoCreator creates a photo object from each photo
	 * @param args
	 */

	public static void photoCreator(){
		//TODO fill in method
	}

	public static void main(String[] args) {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<String> dates = readDates(filePath);
		System.out.println(dates.toString());
	}

  public JPEGReader() {
  }

}
