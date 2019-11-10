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
        Car car = new Car();
        car.setDate = photo.getCreationDate();
        //TODO call OpenALPR
        //TODO set car.license
        //TODO set car.state

        return car;
    }

    public LicenseOCR() {
    }

}
