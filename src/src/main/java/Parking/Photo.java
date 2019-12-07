package Parking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the Photo class for each Photo object, representing each image file loaded into the
 * program.
 */

public class Photo {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // instance varaibles
    BufferedImage image;
    LocalDateTime dateTime;
    String path;
    String md5Hash;

    /**
     * hash of photo which provides us with a unique identifier for each photo to handle duplicates
     */

    /**
     * Constructor for the Photo class and object with BufferedImage parameter
     * 
     * @param image    (BufferedImage)
     * @param dateTime (LocalDateTime)
     * @param md5Hash  (String)
     * @param path     (String)
     * @throws ParkingException
     */
    public Photo(BufferedImage image, LocalDateTime dateTime, String md5Hash, String path)
            throws ParkingException {
        this.image = image;
        this.dateTime = dateTime;
        this.md5Hash = md5Hash;
        this.path = path;
    }

    /**
     * Constructor for the Photo class and object with photo bytes parameter
     * 
     * @param photoBytes (byte[])
     * @param dateTime   (LocalDateTime)
     * @param md5Hash    (String)
     * @param path       (String)
     * @throws ParkingException
     */
    public Photo(byte[] photoBytes, LocalDateTime dateTime, String md5Hash, String path)
            throws ParkingException {
        try {
            InputStream inputStream = new ByteArrayInputStream(photoBytes);
            this.image = Thumbnails.of(inputStream).scale(1).asBufferedImage();
            this.dateTime = dateTime;
            this.md5Hash = md5Hash;
            this.path = path;
        } catch (IOException e) {
            throw new ParkingException("Unable to load from Photo bytes: " + e.getMessage());
        }
    }

    /**
     * Getter method to get the photo's creation date (i.e. when the photo was taken)
     * 
     * @return date and time (LocalDateTime)
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Getter method to get the file path of the photo
     * 
     * @return path (String)
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter method to get the hash string of the photo
     * 
     * @return md5Hash (String)
     */
    public String getMd5Hash() {
        return md5Hash;
    }

    /**
     * Method that overrides the default toString method by concatenating date and time, md5Hash and
     * path. This is shortString with path added.
     * 
     * @return string
     */
    @Override
    public String toString() {
        return toShortString() + ", " + path;
    }

    /**
     * Method that concatenates date and time with md5Hash
     * 
     * @return string
     */
    public String toShortString() {
        return dateTime.format(formatter) + ", " + md5Hash;
    }

    /**
     * This method turns images to jpeg bytes
     * 
     * @return byte[]
     */
    public byte[] toJpegBytes() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).scale(1).outputFormat("jpeg").toOutputStream(outStream);
        } catch (Exception e) {
            // This should not happen if image is valid.
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    /**
     * Getter method for the image instance variable
     * 
     * @return image (BufferedImage)
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Getter method for the thumbnail
     * 
     * @return image (BufferedImage)
     */
    public BufferedImage getThumbnail() {
        try {
            return Thumbnails.of(image).size(128, 128).keepAspectRatio(true).asBufferedImage();
        } catch (Exception e) {
            // This should not happen if image is valid.
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method overrides the default equals method to compare two photo objects
     * 
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
        Photo oPhoto = (Photo) o;
        return dateTime.equals(oPhoto.dateTime) && md5Hash.equals(oPhoto.md5Hash)
                && path.equals(oPhoto.path);
    }
}
