package Parking;

import java.util.ArrayList;

/**
 * ParkingInstanceProcessor creates an arraylist of parkng instances from a
 * folder of photos.
 */
public class ParkingInstanceProcessor {
    JPEGReader jpegReader = new JPEGReader();
    LicenseOCR licenseOCR = new LicenseOCR();
    ArrayList<Car> carsDatabase = new ArrayList<Car>();


    /**
     * createCar method creates a new Car object from the license OCR
     */

    public void addCar() {
        //check that the license don't already exist in carsDatabase
        // add car to carsDatabase
        
    }
     
    public ParkingInstanceProcessor() {

    }
}
