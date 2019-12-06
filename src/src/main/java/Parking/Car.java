package Parking;

/**
 * This is the Car class and each car object represents a single car. A Car object has 2
 * instance variables: (1) License plate number (license) (2) State under which is car
 * is registered, e.g. California = "CA" (state)
 * 
 */

public class Car {
    private String state;
    private String license;

    /**
     * Constructor for Car class
     * 
     * @param license (String)
     * @param state (String)
     */
    public Car(String state, String license) {
        this.state = state;
        this.license = license;
    }

    /**
     * Getter method to get the car license plate number
     * 
     * @return license (String)
     */
    public String getLicense() {
        return license;
    }

    /**
     * Getter method to get the state that the car is registered under
     * (e.g. California would be "CA")
     * 
     * @return state (String)
     */
    public String getState() {
        return state;
    }
    
    /**
     * Method that concatenates state and license with a comma and space.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return state + ", " + license;
    }
    
    /**
     * Method that compares between two Car objects by comparing their
     * state and license values.
     * 
     * @param o (Object)
     * @return boolean
     */
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
