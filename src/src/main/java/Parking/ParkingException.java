package Parking;

public class ParkingException extends Exception {
    private static final long serialVersionUID = 1L;

    // Parameterless Constructor
    public ParkingException() {
    }

    // Constructor that accepts a message
    public ParkingException(String message) {
        super(message);
    }
}

