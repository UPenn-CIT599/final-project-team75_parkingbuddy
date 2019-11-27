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

		try {
	
			try {
				ArrayList<Photo> photos = r.createPhotos(path);
				for (Photo photo: photos){
					Car myCar = ocr.createCar(photo);
					ParkingInstance parkingInstance = pip.createParkingInstance(myCar, photo);
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

	public void addParkingInstanceToDB(Database db, String tableName, ArrayList<ParkingInstance> parkingInstances){
		for (ParkingInstance pi: parkingInstances){
			db.insertParkingInstance(pi, tableName);
		}

	}


	public static void main(String[] args) {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		ArrayList<ParkingInstance> parkingInstanceArr = new ArrayList<ParkingInstance>();
		parkingInstanceArr = pip.createParkingInstanceArray(filePath);
		
		for (ParkingInstance pi : parkingInstanceArr) {
			System.out.println("Parking Instance Array: " + pi.parkingInstanceToString());
		}
	}
	
}
