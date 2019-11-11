package Parking;

import java.util.Date;

/**
 * This is the Photo class for each Photo object, representing each image file loaded into the program. 
 * A Photo object has 3 paramters: 
 * (1) Photo metadata
 * (2) The date of the image file's creation, i.e. when the photo was taken
 * (3) The hash string of the photo as a unique identifier for each photo
 */

class Photo {
    // photo metadata
    Byte[] data;

    // photo creation date, i.e. time that photo was taken of car parking instance
    Date creationDate;

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
    public Photo(Byte[] data, Date creationDate, String photoHash) {
        this.data = data;
        this.creationDate = creationDate;
        this.photoHash = photoHash;
    }

    /**
     * Getter method to get photo data
     * @return
     */
    public Byte[] getData() {
        return data;
    }

    /**
     * Setter method to set photo data
     * @param data
     */
    public void setData(Byte[] data) {
        this.data = data;
    }

    /**
     * Getter method to get the photo's creation date 
     * (i.e. when the photo was taken)
     * @return
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Setter method to set the photo's creation date
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
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

}
