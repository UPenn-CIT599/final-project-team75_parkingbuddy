package Parking;
/**
 * Each ParkingAggregate object has the count of number of times that license parked overnight.
 */
public class ParkingAggregate{
    String license;
    String state;
    int overnightCount;

    public ParkingAggregate(String license, String state, int overnightCount) {
        this.license = license;
        this.state = state;
        this.overnightCount = overnightCount;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getOvernightCount() {
        return overnightCount;
    }

    public void setOvernightCount(int overnightCount) {
        this.overnightCount = overnightCount;
    }

}