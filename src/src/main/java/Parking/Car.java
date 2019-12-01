package Parking;

/**
 * This is the Car class is for each car object, representing a single car. 
 * A Car object has 2 parameters: 
 * (1) License plate number (license)
 * (2) State under which is car is registered, e.g. California = "CA" (state)
 * 
 */

public class Car {
    private String state;
    private String license;
    
    /**
     * Constructor for Car
     * @param license
     * @param state
     */
    public Car(String state, String license) {
        this.state = state;
        this.license = license;
    }

    /**
     * Getter method to get the car license plate number
     * @return
     */
    public String getLicense() {
        return license;
    }

    /**
     * Getter method to get the state that the car is registered under 
     * (e.g. California would be "CA")
     * @return
     */
    public String getState() {
        return state;
    }
    
    public String toString() {
    	return state + ", " + license;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car oCar = (Car) o;
        return state.equals(oCar.state) && license.equals(oCar.license);
    }
}
