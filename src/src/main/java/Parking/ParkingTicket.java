package Parking;

import java.util.*;

/**
 * Each parking ticket represents the car that violates the rule, and the
 * instances when the car violates the rule.
 */

public class ParkingTicket {
    public Car car;
    public ArrayList<ParkingInstance> parkingInstances;
    // optional violationType ENUM if more rules are added;

    
    public ParkingTicket(Car car, ArrayList<ParkingInstance> parkingInstances) {
        this.car = car;
        this.parkingInstances = parkingInstances;
    }


    public Car getCar() {
        return car;
    }


    public void setCar(Car car) {
        this.car = car;
    }


    public ArrayList<ParkingInstance> getParkingInstances() {
        return parkingInstances;
    }


    public void setParkingInstances(
            ArrayList<ParkingInstance> parkingInstances) {
        this.parkingInstances = parkingInstances;
    }


}
