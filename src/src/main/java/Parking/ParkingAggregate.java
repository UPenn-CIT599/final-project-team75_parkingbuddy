package Parking;

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

    public String getState() {
        return car.getState();
    }

    public String getLicense() {
        return car.getLicense();
    }

    public int getOvernightCount() {
        return overnightCount;
    }

    public ArrayList<ParkingInstance> getParkingInstances() {
        return ParkingInstances;
    }

    public void setParkingInstance(ArrayList<ParkingInstance> parkingInstance) {
        ParkingInstances = parkingInstance;
    }
    
    public String toString() {
    	String str = car.getState() + ", " + car.getLicense() + ", " + overnightCount;
    	return str;
    }
}