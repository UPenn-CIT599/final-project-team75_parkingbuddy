package Parking;

import java.util.ArrayList;

/**
 * This class creates ParkingAggregate object has the count of number of times that
 * license parked overnight.
 */
public class ParkingAggregate {
    // instance variables
    Car car;
    int overnightCount;
    ArrayList<ParkingInstance> ParkingInstances;
    
    /**
     * Constructor for the ParkingAggregate class
     * @param car (Car)
     * @param overnightCount (int)
     */
    public ParkingAggregate(Car car, int overnightCount) {
        this.car = car;
        this.overnightCount = overnightCount;
    }
    
    /**
     * Getter method for car instance variable
     * @return car (Car)
     */
    public Car getCar() {
        return car;
    }

    /**
     * Getter method for the state value in car instance variable
     * @return state (String)
     */
    public String getState() {
        return car.getState();
    }
    
    /**
     * Getter method for the license value in car instance variable
     * @return license (String)
     */
    public String getLicense() {
        return car.getLicense();
    }
    
    /**
     * Getter method for the overnightCount instance variable
     * @return overnightCount (int)
     */
    public int getOvernightCount() {
        return overnightCount;
    }
    
    /**
     * Getter method for the ParkingInstances instance variable
     * @return ParkingInstances (ArrayList of ParkingInstance)
     */
    public ArrayList<ParkingInstance> getParkingInstances() {
        return ParkingInstances;
    }
    
    /**
     * Setter method for the ParkingInstances instance variable
     * @param parkingInstance (ArrayList of ParkingInstance)
     */
    public void setParkingInstance(ArrayList<ParkingInstance> parkingInstance) {
        ParkingInstances = parkingInstance;
    }
    
    /**
     * This method concatenates a car's state, license number and overnight count
     * to a string.
     */
    public String toString() {
        String str = car.getState() + ", " + car.getLicense() + ", " + overnightCount;
        return str;
    }
}
