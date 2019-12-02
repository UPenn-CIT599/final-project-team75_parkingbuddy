package Parking;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * ParkingInstanceProcessor creates an Arraylist of parking instances from a
 * folder of photos.
 */

public class ParkingInstanceProcessor {
	LicenseOCR ocr = new LicenseOCR();
	Database db;

	public ParkingInstanceProcessor(Database db) {
		this.db = db;
	}

	/**
	 * This method creates a ParkingInstance object from two arguments: Car and
	 * Photo.
	 * 
	 * @param car
	 * @param photo
	 * @return
	 */
	public ArrayList<ParkingInstance> createParkingInstances(ArrayList<Photo> photos) {
		ArrayList<ParkingInstance> parkingInstances = new ArrayList<ParkingInstance>();
		for (Photo photo : photos) {
			Car myCar = ocr.getCarWithOpenALPR(photo);
			if (myCar == null) {
				continue;
			}
			parkingInstances.add(new ParkingInstance(myCar, photo));
		}
		return parkingInstances;
	}

	public ArrayList<ParkingInstance> createParkingInstances(Path path)
			throws ParkingException {
		ArrayList<Photo> photos = PhotoFactory.createPhotos(path);
		return createParkingInstances(photos);
	}

	public ArrayList<ParkingInstance> createParkingInstances(List<File> files)
			throws ParkingException {
		ArrayList<Photo> photos = PhotoFactory.createPhotos(files);
		return createParkingInstances(photos);
	}

	/**
	 * This method adds multiple parking instance objects from an ArrayList to the
	 * Database.
	 * 
	 * @param db
	 * @param filePath
	 */
	public void addParkingInstancesToDB(
			ArrayList<ParkingInstance> parkingInstances) throws ParkingException {
		if (db == null) {
			throw new ParkingException("Invalid database");
		}
		for (ParkingInstance pi : parkingInstances) {
			db.insertParkingInstance(pi);
		}
	}

	public ArrayList<ParkingInstance> addParkingInstances(Path path)
			throws ParkingException {
		ArrayList<ParkingInstance> parkings = createParkingInstances(path);
		addParkingInstancesToDB(parkings);
		return parkings;
	}

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
			ArrayList<ParkingInstance> parkingInstances =
					pip.createParkingInstances(filePath);
			for (ParkingInstance parkingInstance : parkingInstances) {
				System.out.println(parkingInstance);
			}
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

}
