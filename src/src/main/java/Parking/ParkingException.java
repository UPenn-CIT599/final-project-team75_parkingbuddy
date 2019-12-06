package Parking;
/**
 * Parking Exception used throughout the project and extends the Exception class.
 */
public class ParkingException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     *  Constructor for the ParkingException class
     */
    public ParkingException() {
    }

    /**
     * Constructor that accepts a message 
     * @param message (String)
     */
    public ParkingException(String message) {
        super(message);
    }
}

