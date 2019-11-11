package Parking;

import java.nio.file.Path;
import java.util.*;

/**
 * ParkingTicketProcessor takes user inputted start and end dates, parking
 * instances, path to photos, and generates an ArrayList of parking tickets.
 */
public class ParkingTicketProcessor {
    ParkingReportWriter ticketWriter;

    /**
     * // Check each parking Instance if it is in violate, then add to the
     * ParkingTicketArray if it is a violation.
     * 
     * @param startDate
     * @param endDate
     * @param parkingInstances ArrayList of all the parking instances
     * @param fileInputPath    folder where photos are stored
     * @return
     */
    public ArrayList<ParkingTicket> writeTicket(Date startDate, Date endDate,
            ArrayList<ParkingInstance> parkingInstances, Path fileInputPath) {
            
            ArrayList<ParkingTicket> parkingTickets = new ArrayList<ParkingTicket>();
        // logic to check for overnight parking rule violation

        return parkingTickets;

    }
}
