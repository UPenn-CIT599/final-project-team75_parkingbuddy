package Parking;

import java.time.LocalDateTime;
import java.awt.image.BufferedImage;


/**
 * ParkingInstance contains parking instance object that represents each instance that a car is
 * parked at the parking lot.
 */
public class ParkingInstance {
	// instance variables
	Car car;
	Photo photo;

	/**
	 * Constructor for the ParkingInstance class.
	 * @param car (Car)
	 * @param photo (Photo)
	 */
	public ParkingInstance(Car car, Photo photo) {
		this.car = car;
		this.photo = photo;
	}
	
	/**
	 * Getter for the date and time values from the photo.
	 * @return date and time (LocalDateTime)
	 */
	public LocalDateTime getDateTime() {
		return photo.getDateTime();
	}
	
	/**
	 * Getter for the car instance variable.
	 * @return car (Car)
	 */
	public Car getCar() {
		return car;
	}
	
	/**
	 * Getter for the photo instance variable.
	 * @return photo (Photo)
	 */
	public Photo getPhoto() {
		return photo;
	}
	
	/**
	 * Getter for the state value of the car instance variable.
	 * @return state of car (String)
	 */
	public String getState() {
		return car.getState();
	}
	
	/**
	 * Getter for the license value of the car instance variable.
	 * @return license number of car (String)
	 */
	public String getLicense() {
		return car.getLicense();
	}
	
	/**
	 * Setter for the car instance variable.
	 * @param car
	 */
	public void setCar(Car car) {
		this.car = car;
	}
	
	/**
	 * Getter for the photo instance variable's MD5 Hash.
	 * @return MD5 Hash (String)
	 */
	public String getPhotoMd5Hash() {
		return photo.getMd5Hash();
	}
	
	/**
	 * This method concatenates the instance variables of this class.
	 * @return car and photo (String)
	 */
	public String toString() {
		return car.toString() + ", " + photo.toShortString();
	}
	
	/**
	 * Getter for the thumbnail of the photo instance variable
	 * @return thumbnail (BufferedImage)
	 */
	public BufferedImage getThumbnail() {
		return photo.getThumbnail();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParkingInstance oParkingInstance = (ParkingInstance) o;
		return car.equals(oParkingInstance.car) && photo.equals(oParkingInstance.photo);
	}
}
