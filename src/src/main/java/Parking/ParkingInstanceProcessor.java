package Parking;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ParkingInstanceProcessor creates an Arraylist of parking instances from a
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

	
	/**
	 * This method creates an ArrayList of parking instance objects. 
	 * @param path
	 * @return
	 */
	public ArrayList<ParkingInstance> createParkingInstanceArray(Path path) {
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		ArrayList<ParkingInstance> parkingInstanceArrayList = new ArrayList<ParkingInstance>();
		JPEGReader r = new JPEGReader();
		LicenseOCR ocr = new LicenseOCR(); 

		// check if folder is empty
		String pathString = path.toString();
		if (isEmpty(pathString) == true) {
			System.out.println("Folder is empty.");
			return parkingInstanceArrayList;
		} 
		
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
	

	private static boolean isEmpty(String path) {
		if (Paths.get(path).toFile().listFiles().length == 0) {
			return true;
		}
		
		return false;
	}

	/** 
	 * This method adds multiple parking instance objects from an ArrayList to the Database.
	 * @param db
	 * @param filePath
	 */
	public void addParkingInstancesToDB(Database db, Path filePath){
		ArrayList<ParkingInstance> parkingInstanceArr = new ArrayList<ParkingInstance>();
		parkingInstanceArr = createParkingInstanceArray(filePath);
		for (ParkingInstance pi : parkingInstanceArr) {
			db.insertParkingInstance(pi);
		}
	}


	public static void main(String[] args) {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
		ArrayList<ParkingInstance> parkingInstanceArr = new ArrayList<ParkingInstance>();
		parkingInstanceArr = pip.createParkingInstanceArray(filePath);
		
		for (ParkingInstance pi : parkingInstanceArr) {
			System.out.println("Parking Instance Array: " + pi.toString());
		}
	}
	
}
