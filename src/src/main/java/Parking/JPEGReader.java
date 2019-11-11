package Parking;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.drew.imaging.*;
import com.drew.metadata.*;
import com.drew.metadata.exif.*;

/**
 * This class reads the image files and extracts the EXIF metadata required to construct a Photo object
 * from each image. 
 *
 */
public class JPEGReader {

	/**
	 * This method extracts the original date of the files from a folder by
	 * extracting exif data and storing them in an ArrayList of Strings.
	 * 
	 * @param path
	 * @return ArrayList<String>
	 */
	public ArrayList<String> readDates(Path path) {
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
						String pattern = "yyyy-MM-dd";
						String formattedDate = new SimpleDateFormat(pattern).format(date);
						dates.add(formattedDate);
						
					// exception handling
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
	 * buildPhotoObject creates a photo object from each photo.
	 * @param args
	 */
	public void buildPhotoObject(){
		//TODO fill in method
		
	}

	public static void main(String[] args) {
		JPEGReader r = new JPEGReader();
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ArrayList<String> dates = r.readDates(filePath);
		System.out.println(dates.toString());
	}

	public JPEGReader() {

	}

}
