package Parking;

/**
 * Car class is for each car object, it represents a single car. Car object has
 * 2 instance variables, license (license plates) and state (State (e.g. PA) on
 * the license plate.)
 */
class Car {
    public String license;
    public String state;
    

    public Car(String license, String state) {
        this.license = license;
        this.state = state;
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


}
