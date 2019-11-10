package Parking;

/**
 * LicenseOCR calls the OpenALPR API to read license plate from each photo.
 */
class LicenseOCR {
    /**
     * LicenseOCR takes in a photo object and returns a car object.
     * @param photo
     * @return
     */
    public Car getCar(Photo photo){
        //TODO call OpenALPR to get license and state

        String license = "license from Open ALPR";
        String state = "PA";

        Car car = new Car(license, state);
        return car;
    }

    public LicenseOCR() {
    }

}
