package Parking;

import java.time.LocalDateTime;


/**
 * ParkingInstance contains parking instance object that represents each
 * instance that a car is parked at the parking lot.
 */
public class ParkingInstance {
    LocalDateTime dateTime;
    Car car;
	String photoHash;

	public ParkingInstance(Car car, Photo photo) {
    	this.dateTime = photo.getDateTime();
    	this.car = car;
    	this.photoHash = photo.getMd5Hash();
	}
    
    public ParkingInstance(LocalDateTime date, Car car, String photoHash) {
    	this.dateTime = date;
    	this.car = car;
    	this.photoHash = photoHash;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime date) {
		this.dateTime = date;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getPhotoHash() {
		return photoHash;
	}

	public void setPhotoHash(String photoHash) {
		this.photoHash = photoHash;
	}
    
	public String toString() {
		String dateStr = dateTime.toString();
		String carStr = car.getLicense() + "," + car.getState();
		String photoHashStr = photoHash;
		String parkingInstanceStr = dateStr + "," + carStr + "," + photoHashStr;
		
		return parkingInstanceStr;
	}
    
}
