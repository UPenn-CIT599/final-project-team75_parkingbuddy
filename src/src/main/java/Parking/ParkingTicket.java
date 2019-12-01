package Parking;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Each parking ticket represents the car that violated the rule, and the
 * instances when the car violates the rule.
 */

public class ParkingTicket {
    Car car;
    int overnightCount;
    ArrayList<LocalDate> datesParked;
    
    // optional violationType ENUM if more rules are added;
    
    public ParkingTicket(Car car, int overnightCount, ArrayList<LocalDate> datesParked) {
    	this.car = car;
    	this.overnightCount = overnightCount;
    	this.datesParked = datesParked;
    }


}
