package Parking;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * ParkingInstanceProcessor creates an Arraylist of parking instances from a
 * folder of photos.
 */

public class ParkingInstanceProcessor {
	LicenseOCR ocr = new LicenseOCR();

	/**
	 * This method creates a ParkingInstance object from two arguments: Car and
	 * Photo.
	 * 
	 * @param car
	 * @param photo
	 * @return
	 */
	public ArrayList<ParkingInstance> createParkingInstances(
			ArrayList<Photo> photos) {
		ArrayList<ParkingInstance> parkingInstances =
				new ArrayList<ParkingInstance>();
		for (Photo photo : photos) {
			Car myCar = ocr.getCarWithOpenALPR(photo);
			parkingInstances.add(new ParkingInstance(myCar, photo));
		}
		return parkingInstances;
	}

	public ArrayList<ParkingInstance> createParkingInstances(Path path) throws PhotoException {
		ArrayList<Photo> photos = PhotoFactory.createPhotos(path);
		return createParkingInstances(photos);
	}

	/**
	 * This method adds multiple parking instance objects from an ArrayList to
	 * the Database.
	 * 
	 * @param db
	 * @param filePath
	 */
	public void addParkingInstancesToDB(Database db,
			ArrayList<ParkingInstance> parkingInstances) {
		for (ParkingInstance pi : parkingInstances) {
			db.insertParkingInstance(pi);
		}
	}

	public void addParkingInstances(Database db, Path path)
			throws PhotoException {
		addParkingInstancesToDB(db, createParkingInstances(path));
	}


	public static void main(String[] args) {
		Path filePath =
				Paths.get("src/test/java/Parking/MultipleImagesFolder/");
		try {
			ArrayList<Photo> photos = PhotoFactory.createPhotos(filePath);
			ParkingInstanceProcessor pip = new ParkingInstanceProcessor();
			ArrayList<ParkingInstance> parkingInstances =
					pip.createParkingInstances(photos);
			for (ParkingInstance parkingInstance : parkingInstances) {
				System.out.println(parkingInstance);
			}
			for (Photo photo : photos) {
				System.out.println(photo);
			}
		} catch (PhotoException e) {
			e.printStackTrace();
		}
	}

}
