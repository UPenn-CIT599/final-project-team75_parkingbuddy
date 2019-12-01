package Parking;

public class PhotoException extends Exception {
    private static final long serialVersionUID = 1L;

    // Parameterless Constructor
    public PhotoException() {
    }

    // Constructor that accepts a message
    public PhotoException(String message) {
        super(message);
    }
}

