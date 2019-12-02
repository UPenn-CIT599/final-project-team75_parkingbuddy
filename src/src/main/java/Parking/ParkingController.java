package Parking;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ParkingController directs the program based on user input on whether to add
 * parking instances or to pull parking violation report.
 * 
 * If user chooses to pull a violation reports, this class contains the logic to 
 * call all relevant classes to process tickets or add parking instances to the SQL database.
 */

class ParkingController {
    Database db = new Database();
    ParkingInstanceProcessor pip = new ParkingInstanceProcessor();


    /**
     * Add parking instances to the database based on folder or image file input.
     * @param filePath
     */
    public ArrayList<ParkingInstance> uploadPhotos(Path filePath) throws PhotoException {
        return pip.addParkingInstances(db, filePath);
    }

    public ArrayList<ParkingInstance> uploadPhotos(List<File> files) throws PhotoException {
        return pip.addParkingInstances(db, files);
    }

    /**
     * This method creates a violation report based on the user inputted timeframe.
     * @param startDate
     * @param endDate
     * @return
     */
    public ArrayList<ParkingAggregate> getParkingAggregates(LocalDate startDate, LocalDate endDate){
        ArrayList<ParkingAggregate> parkingResults =  db.getAggregatedParkingInstances(startDate,endDate);
        return parkingResults;
    }

    /**
     * Parking Controller constructor
     */
    public ParkingController() {
    }

    public static void main(String[] args) {
        ParkingController pc = new ParkingController();
        // Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
        // pc.uploadPhotos(filePath);
        ArrayList<ParkingAggregate> results = new ArrayList<ParkingAggregate>();
        results = pc.getParkingAggregates(LocalDate.of(2010, 2, 11),LocalDate.of(2019, 6, 11));
        for (ParkingAggregate result : results) {
        	String str = result.toString();
        	System.out.println(str);
        }
 
    }
}
