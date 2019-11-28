package Parking;

import java.time.LocalDateTime;

/**
 * This is the Photo class for each Photo object, representing each image file loaded into the program. 
 * A Photo object has 3 parameters: 
 * (1) Photo metadata
 * (2) The date of the image file's creation, i.e. when the photo was taken
 * (3) The hash string of the photo as a unique identifier for each photo
 */

class Photo {
    // photo metadata
    byte[] data;

    // photo creation date, i.e. time that photo was taken of car parking instance
    LocalDateTime creationDate;

    // hash of photo which provides us with a unique identifier for each photo to handle duplicates
    String photoHash;
    
    // path of photo file based on user input
    String photoFilePath;

    /**
     * Constructor for Photo
     * @param data
     * @param creationDate
     * @param photoHash
     */
    public Photo(byte[] data, LocalDateTime creationDate, String photoHash, String photoFilePath) {
        this.data = data;
        this.creationDate = creationDate;
        this.photoHash = photoHash;
        this.photoFilePath = photoFilePath;
    }

    /**
     * Getter method to get photo data
     * @return
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Setter method to set photo data
     * @param data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Getter method to get the photo's creation date 
     * (i.e. when the photo was taken)
     * @return
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Setter method to set the photo's creation date
     * @param creationDate
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Getter method to get the hash string of the photo
     * @return
     */
    public String getPhotoHash() {
        return photoHash;
    }

    /**
     * Setter method to set the hash string of the photo
     * @param photoHash
     */
    public void setHash(String photoHash) {
        this.photoHash = photoHash;
    }
    
    /**
     * Getter method to get the file path of the photo
     * @return
     */
    public String getPhotoFilePath() {
        return photoFilePath;
    }
    
    public String getPhotoToString() {
    	String str = this.creationDate + "," + this.photoHash + "," + this.photoFilePath;
    	return str;
    }

}
