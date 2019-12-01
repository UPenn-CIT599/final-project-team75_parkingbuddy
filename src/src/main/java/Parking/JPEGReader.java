package Parking;

import java.io.*; 
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.drew.imaging.*;
import com.drew.metadata.*;
import com.drew.metadata.exif.*;
import org.apache.commons.io.FilenameUtils;

/**
 * This class reads the image files and extracts the EXIF metadata required to
 * construct a Photo object from each image.
 *
 */
public class JPEGReader {

	/**
	 * This method creates an ArrayList of Photo objects from each image file in
	 * the designated folder.
	 * 
	 * @param path
	 * @return
	 */
	public ArrayList<Photo> createPhotos(Path path) {
		// initialize ArrayList of photo objects
		ArrayList<Photo> photoArrayList = new ArrayList<Photo>();
		
		// check if folder is empty
		String pathString = path.toString();
		if (isEmpty(pathString) == true) {
			System.out.println("Folder is empty.");
			return photoArrayList;
		} 
		
		try {
			// get paths to the image files in the folder
			File[] aFiles = path.toFile().listFiles();
			ArrayList<File> files = new ArrayList<File>();
			for (File file : aFiles) {
				String ext = FilenameUtils.getExtension(file.getName());
				if (!ext.equals("jpeg") && !ext.equals("jpg")) {
					System.out.println("File format for " + file.getName() + " is invalid. Please upload only jpeg or jpg files.");
					continue;
				}
				
				files.add(file);
			}

			// iterate through each image file in the folder to create a photo object
			try {
				for (int i = 0; i < files.size(); i++) {
					try {
						System.out.println("Scanning photo " + (i + 1) + "/"
								+ files.size());

						// get photo file path
						String filePath = files.get(i).toString();

						// get byte data of photo
						Path pathToImageFile = Paths.get(filePath);
						byte[] data = Files.readAllBytes(pathToImageFile);

						// get metadata from the file and obtain Exif date and
						// time
						LocalDateTime formattedDate = readDates(files.get(i));

						// get random UUID for this photo
						String md5String = getMd5(filePath);

						// create photo object
						Photo myPhoto = new Photo(data, formattedDate, md5String, filePath);
						System.out.println(myPhoto.getPhotoToString() + "\n");

						photoArrayList.add(myPhoto);

						// exception handling
					} catch (NullPointerException e) {
						System.out.println("File " + files.get(i).toString()
								+ " doesn't have metadata.");
					} catch (IOException e) {
						System.out.println("File \"" + files.get(i).toString()
								+ "\" cannot read metadata.");
					}
				}
			}
			catch (NullPointerException e) {
				System.out.println("The folder is empty");
			}
		} catch (ProviderNotFoundException e) {
			System.out.println("Given path is invalid");
		}

		return photoArrayList;
	}
	

	
	/**
	 * This method creates a Photo object from an image file. 
	 * Used to create individual photo objects from the File argument.
	 * 
	 * @param file
	 * @return
	 */
	public Photo createPhoto(File file) {
		Photo myPhoto = null;

		try {
			String filePath = file.toString();
			Path pathToImageFile = Paths.get(filePath);
			byte[] data = Files.readAllBytes(pathToImageFile);
			LocalDateTime formattedDate = readDates(file);
			String md5String = getMd5(filePath);

			// create Photo object
			myPhoto = new Photo(data, formattedDate, md5String, filePath);

			// exception handling
		} catch (NullPointerException e) {
			System.out.println(
					"File " + file.toString() + " doesn't have metadata.");
		} catch (IOException e) {
			System.out.println(
					"File \"" + file.toString() + "\" cannot read metadata.");
		}
		return myPhoto;
	}


	/**
	 * This is a private method that extracts the original date of the files
	 * from a folder by extracting exif data and storing them in an ArrayList of
	 * Strings.
	 * 
	 * Note: To get date from LocalDateTime object: formattedDate.toLocalDate()
	 * To get time from LocalDateTime object: formattedDate.toLocalTime()
	 * 
	 * @param file
	 * @return
	 */
	private LocalDateTime readDates(File file) {
		Metadata metadata;
		LocalDateTime formattedDate = null;
		try {
			System.out.println(file);
			metadata = ImageMetadataReader.readMetadata(file);
			ExifSubIFDDirectory dir =
					metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			String dateStr =
					dir.getString(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
			// System.out.println(dateStr);
			String pattern = "yyyy:MM:dd HH:mm:ss";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			formattedDate = LocalDateTime.parse(dateStr, formatter);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	
	/**
	 * This is a private method to generate the md5 hash of an image file.
	 * Referred to the following StackOverflow post: "https://stackoverflow.com/questions/13152736/how-to-generate-an-md5-checksum-for-a-file-in-android"
	 * @param filePath
	 * @return
	 */
	private String getMd5(String filePath) {
		InputStream inputStream = null;
		try {
	        inputStream = new FileInputStream(filePath);
	        byte[] buffer = new byte[1024];
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        int numRead = 0;
	        while (numRead != -1) {
	            numRead = inputStream.read(buffer);
	            if (numRead > 0)
	                digest.update(buffer, 0, numRead);
	        }
	        byte [] md5Bytes = digest.digest();
	        
	        return convertHashToString(md5Bytes);
	        
	    } catch (Exception e) {
	        return null;
	        
	    } finally {
	        if (inputStream != null) {
	            try {
	                inputStream.close();
	            } catch (Exception e) { }
	        }
	    }
	}

	/**
	 * This is a method to convert the md5 hash in byte to String.
	 * Referred to the following StackOverflow post: "https://stackoverflow.com/questions/13152736/how-to-generate-an-md5-checksum-for-a-file-in-android"
	 * @param md5Bytes
	 * @return
	 */
	private String convertHashToString(byte[] md5Bytes) {
		String returnVal = "";
		for (int i = 0; i < md5Bytes.length; i++) {
			returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
		}
		return returnVal.toUpperCase();
	}
	
//	public static boolean isValidPath(String path) {
//	    try {
//	        Paths.get(path);
//	    } catch (InvalidPathException | NullPointerException ex) {
//	        return false;
//	    }
//	    return true;
//	}
	
	public static boolean isEmpty(String path) {
		if (Paths.get(path).toFile().listFiles().length == 0) {
			return true;
		}
		return false;
	}

	
	public static void main(String[] args) {
		JPEGReader r = new JPEGReader();
		Path filePath = Paths.get("src/test/java/Parking/EmptyFolder/");
		ArrayList<Photo> pal = r.createPhotos(filePath);
		for (Photo photo : pal) {
			System.out.println(photo.getPhotoToString());
		}
		
	}


}
