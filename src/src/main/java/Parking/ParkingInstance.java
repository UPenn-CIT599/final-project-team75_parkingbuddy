package Parking;

import java.time.LocalDateTime;
import java.awt.image.BufferedImage;


/**
 * ParkingInstance contains parking instance object that represents each instance that a car is
 * parked at the parking lot.
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

	public String getState() {
		return car.getState();
	}

	public String getLicense() {
		return car.getLicense();
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

	public BufferedImage getThumbnail() {
		return photo.getThumbnail();
	}

	public Photo getPhoto() {
		return photo;
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
