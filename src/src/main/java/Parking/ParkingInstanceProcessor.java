package Parking;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * ParkingInstanceProcessor creates an ArrayList of parking instances images and the source of
 * images can vary from folders to files.
 */
public class ParkingInstanceProcessor {
	// instance variables
	LicenseOCR ocr = new LicenseOCR();
	Database db;

	/**
	 * Constructor for the ParkingInstanceProcessor class
	 * 
	 * @param db (Database)
	 */
	public ParkingInstanceProcessor(Database db) {
		this.db = db;
	}

	/**
	 * This method creates ParkingInstance objects from photos and store them in ArrayList
	 * 
	 * @param ArrayList of photo
	 * @return ArrayList of ParkingInstance
	 */
	public ArrayList<ParkingInstance> createParkingInstances(ArrayList<Photo> photos) {

		ArrayList<ParkingInstance> parkingInstances = new ArrayList<ParkingInstance>();

		// iterate through each photo and find a car in that photo, then store it as instance
		for (Photo photo : photos) {
			Car myCar = ocr.getCarWithOpenALPR(photo);
			if (myCar == null) {
				continue;
			}
			parkingInstances.add(new ParkingInstance(myCar, photo));
		}
		return parkingInstances;
	}

	/**
	 * This method creates ArrayList of ParkingInstance by taking in a path parameter finding photos
	 * in that path to eventually create an ArrayList of ParkingInstance
	 * 
	 * @param path (Path)
	 * @return ArrayList of Parking Instance
	 * @throws ParkingException
	 */
	public ArrayList<ParkingInstance> createParkingInstances(Path path) throws ParkingException {
		ArrayList<Photo> photos = PhotoFactory.createPhotos(path);
		// calling the createParkingInstances method now with ArrayList of photos
		return createParkingInstances(photos);
	}

	/**
	 * This method creates ArrayList of ParkingInstance by taking in a List of files, ideally photos
	 * to eventually create an ArrayList of ParkingInstance
	 * 
	 * @param path (Path)
	 * @return ArrayList of Parking Instance
	 * @throws ParkingException
	 */
	public ArrayList<ParkingInstance> createParkingInstances(List<File> files)
			throws ParkingException {
		ArrayList<Photo> photos = PhotoFactory.createPhotos(files);
		// calling the createParkingInstances method now with ArrayList of photos
		return createParkingInstances(photos);
	}

	/**
	 * This method adds multiple parking instance objects from an ArrayList to the Database.
	 * 
	 * @param ArrayList of ParkingInstance
	 * @throws ParkingException
	 */
	public void addParkingInstancesToDB(ArrayList<ParkingInstance> parkingInstances)
			throws ParkingException {
		if (db == null) {
			throw new ParkingException("Invalid database");
		}
		// iterating through each parking instance and adding it to the database
		for (ParkingInstance pi : parkingInstances) {
			db.insertParkingInstance(pi);
		}
	}

	/**
	 * This method creates ArrayList of parking instances and calls parking instances from the path
	 * parameter.
	 * 
	 * @param path (Path)
	 * @return ArrayList of ParkingInstance
	 * @throws ParkingException
	 */
	public ArrayList<ParkingInstance> addParkingInstances(Path path) throws ParkingException {
		ArrayList<ParkingInstance> parkings = createParkingInstances(path);
		addParkingInstancesToDB(parkings);
		return parkings;
	}

	/**
	 * This method creates ArrayList of parking instances and calls parking instances from the
	 * parameter of list of files.
	 * 
	 * @param List of files
	 * @return ArrayList of ParkingInstance
	 * @throws ParkingException
	 */
	public ArrayList<ParkingInstance> addParkingInstances(List<File> files)
			throws ParkingException {
		ArrayList<ParkingInstance> parkings = createParkingInstances(files);
		addParkingInstancesToDB(parkings);
		return parkings;
	}

	public static void main(String[] args) {
		Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		try {
			ParkingInstanceProcessor pip = new ParkingInstanceProcessor(null);
			ArrayList<ParkingInstance> parkingInstances = pip.createParkingInstances(filePath);
			for (ParkingInstance parkingInstance : parkingInstances) {
				System.out.println(parkingInstance);
			}
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}
}
