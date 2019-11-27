package Parking;

import java.time.LocalDateTime;


/**
 * ParkingInstance contains parking instance object that represents each
 * instance that a car is parked at the parking lot.
 */
public class ParkingInstance {
    LocalDateTime date;
    Car car;
	String photoHash;
    
    public ParkingInstance(LocalDateTime date, Car car, String photoHash) {
    	this.date = date;
    	this.car = car;
    	this.photoHash = photoHash;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
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
		String dateStr = date.toString();
		String carStr = car.getLicense() + "," + car.getState();
		String photoHashStr = photoHash;
		String parkingInstanceStr = dateStr + "," + carStr + "," + photoHashStr;
		
		return parkingInstanceStr;
	}
    
}
