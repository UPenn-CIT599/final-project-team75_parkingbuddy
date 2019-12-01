package Parking;

import java.time.LocalDateTime;


/**
 * ParkingInstance contains parking instance object that represents each
 * instance that a car is parked at the parking lot.
 */
public class ParkingInstance {
    Car car;
	Photo photo;

	public ParkingInstance(Car car, Photo photo) {
    	this.car = car;
    	this.photo = photo;
	}

	public LocalDateTime getDateTime() {
		return photo.getDateTime();
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getPhotoMd5Hash() {
		return photo.getMd5Hash();
	}
    
	public String toString() {
		return car.toString() + ", " + photo.toShortString();
	}
    
}
