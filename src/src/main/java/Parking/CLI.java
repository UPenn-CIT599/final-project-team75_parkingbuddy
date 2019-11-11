package Parking;

/**
 * Command Line Interface (CLI) that enables users to take the following actions: 
 * (1) Upload photos (can be in a folder) 
 * (2) Generate report of violations within a certain time frame (user must input the start and end date)
 */

import java.util.*;

public class CLI {
    public Scanner scanner;

    /**
     * method to ask users if they want to upload photos or pull violation ticket. If return True, users want to upload photos. If false, users want to input voolation dates.
     * @return
     */
    public boolean isUploadFolder(){
        //method to ask users if they want to upload photos or pull violation ticket.
        return true;
    }

    /**
     * returns user inputted start date
     * @return
     */
    public Date violationStartDate(){
        //TODO change dates to read user input
        Date startDate = new GregorianCalendar(2004, 6, 10, 13, 45).getTime() ;
        return startDate;
    }

    /**
     * returns user inputted end date
     * @return
     */
    public Date violationEndDate(){
        //TODO change dates to read user input
        Date endDate = new GregorianCalendar(2006, 6, 10, 13, 45).getTime();
        return endDate;
    }

    public static void main(String[] args){
        ParkingInstanceProcessor parkingInstanceProcessor = new ParkingInstanceProcessor();
        ParkingTicketProcessor parkingTicketProcessor = new ParkingTicketProcessor();
        ParkingInstanceDatabase parkingInstanceDatabase = new ParkingInstanceDatabase();

        System.out.println("complete");

    }

}
