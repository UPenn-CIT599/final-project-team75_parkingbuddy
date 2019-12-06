package Parking;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ParkingController directs the program based on user input on whether to add parking instances or
 * to pull parking violation report.
 * 
 * If user chooses to pull a violation reports, this class contains the logic to call all relevant
 * classes to process tickets or add parking instances to the SQL database.
 */

public class ParkingController {
    // instance variables
    Database db;
    ParkingInstanceProcessor pip;

    static ParkingController parkingController;

    /**
     * Constructor for the ParkingController class
     * @throws ParkingException
     */
    private ParkingController() throws ParkingException {
        db = new Database();
        pip = new ParkingInstanceProcessor(db);
    }
    
    /**
     * Another constructor for the Parking Controller class with a given database.
     * @param db (Database)
     */
    public ParkingController(Database db) {
        this.db = db;
        pip = new ParkingInstanceProcessor(db);
    }
    
    /**
     * Method that captures the instance of Parking Controller object
     * @return ParkingController
     * @throws ParkingException
     */
    public static synchronized ParkingController getInstance() throws ParkingException {
        if (parkingController == null) {
            parkingController = new ParkingController();
        }
        return parkingController;
    }


    /**
     * Add parking instances to the database based on folder path
     * @param filePath (Path)
     * @return ArrayList of Parking Instance
     * @throws ParkingException
     */
    public ArrayList<ParkingInstance> uploadPhotos(Path filePath) throws ParkingException {
        return pip.addParkingInstances(filePath);
    }
    
    /**
     * Add parking instances to the database based on the list of files
     * @param files (List of file)
     * @return ArrayList of Parking Instance
     * @throws ParkingException
     */
    public ArrayList<ParkingInstance> uploadPhotos(List<File> files) throws ParkingException {
        return pip.addParkingInstances(files);
    }

    /**
     * This method creates a violation report based on the user input time frame.
     * 
     * @param startDate (LocalDate)
     * @param endDate (LocalDatE)
     * @return ArrayList of ParkingAggregate
     */
    public ArrayList<ParkingAggregate> getParkingAggregates(LocalDate startDate,
            LocalDate endDate) {
        ArrayList<ParkingAggregate> parkingResults = db.getParkingAggregates(startDate, endDate);
        return parkingResults;
    }

    public static void main(String[] args) {
        try {
            ParkingController pc = new ParkingController();

            ArrayList<ParkingAggregate> results = new ArrayList<ParkingAggregate>();
            results = pc.getParkingAggregates(LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
            for (ParkingAggregate result : results) {
                String str = result.toString();
                System.out.println(str);
            }

        } catch (ParkingException e) {
            e.printStackTrace();
        }
    }
}