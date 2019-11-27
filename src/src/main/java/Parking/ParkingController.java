package Parking;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * ParkingController directs the program based on user input on whether to add
 * parking instances or to pull parking violation report.
 */

class ParkingController {
    Database db = new Database();
    ParkingInstanceProcessor pip = new ParkingInstanceProcessor();

    /**
     * Logic to call all the other classes to process tickets or add parking
     * instances.
     */

    public void uploadPhotos(Path filePath){
        pip.addParkingInstances(db, filePath);
    }

    public void pullViolationReport(LocalDate startDate, LocalDate endDate){
    }

    /**
     * Parking Controller constructor
     */
    public ParkingController() {
    }

    public static void main(String[] args) {
        ParkingController pc = new ParkingController();
        Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
        pc.uploadPhotos(filePath);
    }


}
