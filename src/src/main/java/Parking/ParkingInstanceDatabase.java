package Parking;
import java.util.ArrayList;

/**
 * Database to store all the parking instances (each time a car is parked)
 */
class ParkingInstanceDatabase {
    ArrayList<ParkingInstance> instances = new ArrayList<ParkingInstance>();

    /**
     * Get parking instances to get database
     * 
     * @return
     */
    public ArrayList<ParkingInstance> getInstances() {
        return instances;
    }

    /**
     * Add new parking Instances when new photos are uploaded.
     * 
     * @param newInstances
     */
    public void addInstances(ArrayList<ParkingInstance> newInstances) {
        this.instances.addAll(newInstances);

    }

    /**
     * ParkingInstanceDatabase Constructor
     * @param instances
     */
    public ParkingInstanceDatabase() {
        
        }

}
