package Parking;

/**
 * This is the Car class is for each car object, representing a single car. 
 * A Car object has 2 parameters: 
 * (1) License plate number (license)
 * (2) State under which is car is registered, e.g. California = "CA" (state)
 * 
 */

class Car {
    private String license;
    private String state;
    
    /**
     * Constructor for Car
     * @param license
     * @param state
     */
    public Car(String license, String state) {
        this.license = license;
        this.state = state;
    }

    /**
     * Getter method to get the car license plate number
     * @return
     */
    public String getLicense() {
        return license;
    }

    /**
     * Setter method to set the car license plate number
     * @param license
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * Getter method to get the state that the car is registered under 
     * (e.g. California would be "CA")
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method to set the state that the car is registered under
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
    
    public String getLicensePlateAndState() {
    	String str = license + "," + state;
    	return str;
    }

}
