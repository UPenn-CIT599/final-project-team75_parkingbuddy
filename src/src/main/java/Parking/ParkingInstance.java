package Parking;

import java.time.LocalDateTime;
import java.awt.image.BufferedImage;

/**
 * ParkingInstance class contains parking instance object that represents each instance that a car
 * is parked at the parking lot.
 */
public class ParkingInstance {
  // instance variables
  Car car;
  Photo photo;

  /**
   * Constructor for the ParkingInstance class.
   * 
   * @param car   (Car)
   * @param photo (Photo)
   */
  public ParkingInstance(Car car, Photo photo) {
    this.car = car;
    this.photo = photo;
  }

  /**
   * Getter method for the date and time from the photo instance variable
   * 
   * @return date and time (LocalDateTime)
   */
  public LocalDateTime getDateTime() {
    return photo.getDateTime();
  }

  /**
   * Getter method for the car instance variable
   * 
   * @return car (Car)
   */
  public Car getCar() {
    return car;
  }

  /**
   * Getter method for the state value from the car instance variable
   * 
   * @return state (String)
   */
  public String getState() {
    return car.getState();
  }

  /**
   * Getter for the license value of the car instance variable.
   * 
   * @return license number of car (String)
   */
  public String getLicense() {
    return car.getLicense();
  }

  /**
   * Setter method for the car instance variable
   * 
   * @param car (Car)
   */
  public void setCar(Car car) {
    this.car = car;
  }

  /**
   * Getter method for the MD5Hash value of the photo instance variable
   * 
   * @return Md5Hash (String)
   */
  public String getPhotoMd5Hash() {
    return photo.getMd5Hash();
  }

  /**
   * toString method that overrides the default method by concatenating car and string instance
   * variables with a comma and space in between
   * 
   * @return concatenated String
   */
  @Override
  public String toString() {
    return car.toString() + ", " + photo.toShortString();
  }

  /**
   * Getter method for the thumbnail image from the photo instance variable
   * 
   * @return thumbnail (BufferedImage)
   */
  public BufferedImage getThumbnail() {
    return photo.getThumbnail();
  }

  /**
   * Getter method for the photo instance variable
   * 
   * @return photo (Photo)
   */
  public Photo getPhoto() {
    return photo;
  }

  /**
   * equals method that overrides the default. Compares instance variables from two ParkingInstance
   * objects.
   * 
   * @param o (Object)
   * @return boolean
   */
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
