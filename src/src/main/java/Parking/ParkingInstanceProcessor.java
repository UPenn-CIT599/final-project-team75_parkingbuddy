package Parking;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ParkingInstanceProcessor creates an arraylist of parking instances from a
 * folder of photos.
 */

public class ParkingInstanceProcessor {
	
	/**
	 * This method creates a ParkingInstance object from two arguments: Car and Photo. 
	 * @param car
	 * @param photo
	 * @return
	 */
	public ParkingInstance createParkingInstance(Car car, Photo photo) {
		LocalDateTime date = photo.getCreationDate();
		String photoHash = photo.getPhotoHash();
		ParkingInstance parkingInstance = new ParkingInstance(date, car, photoHash);

		return parkingInstance;
	}

	public ArrayList<ParkingInstance> createParkingInstanceArray(Path path) {
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		ArrayList<ParkingInstance> parkingInstanceArrayList = new ArrayList<ParkingInstance>();
		JPEGReader r = new JPEGReader();
		LicenseOCR ocr = new LicenseOCR(); 
		// path = Paths.get("src/test/java/Parking/MultipleImagesFolder/");

		// loops through folder with image files to create from each file: (1) a Photo object, (2) a Car object, and (3) a ParkingInstance object
		// the ParkingInstance object is stored in an ArrayList<ParkingInstance>
		try {
			// get paths to the image files in the folder
			File[] files = path.toFile().listFiles();
			// iterate through each image file in the folder to create a photo object
			try {
				for (int i = 0; i < files.length; i++) {
					System.out.println("Scanning photo " + (i+1) + "/" + files.length);
					File file = files[i];
					Photo myPhoto = r.createPhoto(file);
					Car myCar = ocr.createCar(myPhoto);
					ParkingInstance parkingInstance = pip.createParkingInstance(myCar, myPhoto);
					parkingInstanceArrayList.add(parkingInstance);
				}
				
			} 
			catch (NullPointerException e) {
				System.out.println("The folder is empty");
			} 
		} 
		catch (ProviderNotFoundException e) {
			System.out.println("Given path is invalid");
		}

		return parkingInstanceArrayList;
	}


	public static void main(String[] args) {
		Path filePath = Paths.get("src/src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		ArrayList<ParkingInstance> parkingInstanceArr = new ArrayList<ParkingInstance>();
		parkingInstanceArr = pip.createParkingInstanceArray(filePath);
		for (ParkingInstance pi : parkingInstanceArr) {
			System.out.println("Parking Instance Array: " + pi.parkingInstanceToString());
		}
	}
	
}
