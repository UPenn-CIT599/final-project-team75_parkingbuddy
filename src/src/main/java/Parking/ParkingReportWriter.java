package Parking;

import java.io.File;
// import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Write to output file of the parking tickets, and deal with proper formatting in output file.
 */

public class ParkingReportWriter{
    ArrayList<ParkingTicket> parkingTickets;
    Car car;

    /**
     * write method generates the violation report and outputs a file
     * @return
     */
    public File write(){
        //Path path = ""  user specify where to create new fle
        File violationReport = new File("report.csv");
        return violationReport;
    }

}