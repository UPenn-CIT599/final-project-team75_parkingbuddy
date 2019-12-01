package Parking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Each ParkingAggregate object has the count of number of times that license
 * parked overnight.
 */
public class ParkingAggregate{
    Car car;
    int overnightCount;
    ArrayList<ParkingInstance> ParkingInstances;

    public ParkingAggregate(Car car, int overnightCount) {
        this.car = car;
        this.overnightCount = overnightCount;
    }

    public Car getCar() {
        return car;
    }

    public int getOvernightCount() {
        return overnightCount;
    }

    public ArrayList<ParkingInstance> getParkingInstances() {
        return ParkingInstances;
    }

    // TODO: Remove this.
    public void setParkingInstance(ArrayList<ParkingInstance> parkingInstance) {
        ParkingInstances = parkingInstance;
    }
    
    public String toString() {
    	String str = car.getState() + ", " + car.getLicense() + ", " + overnightCount;
    	return str;
    }
}