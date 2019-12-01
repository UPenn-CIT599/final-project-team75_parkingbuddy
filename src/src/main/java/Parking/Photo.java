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
 * This is the Photo class for each Photo object, representing each image file
 * loaded into the program. A Photo object has 3 parameters:
 * 
 * (1) Photo metadata
 * 
 * (2) The date of the image file's creation, i.e. when the photo was taken
 * 
 * (3) The hash string of the photo as a unique identifier for each photo
 */

public class Photo {
    final static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    BufferedImage image;

    // photo creation date, i.e. time that photo was taken of car parking
    // instance
    LocalDateTime dateTime;

    // hash of photo which provides us with a unique identifier for each photo
    // to handle duplicates
    String md5Hash;

    // path of photo file based on user input
    String path;

    /**
     * Constructor for Photo
     * 
     * @param data
     * @param creationDate
     * @param photoHash
     */
    public Photo(BufferedImage image, LocalDateTime dateTime, String md5Hash,
            String path) throws IOException {
        this.image = image;
        this.dateTime = dateTime;
        this.md5Hash = md5Hash;
        this.path = path;
    }

    public Photo(byte[] photoBytes, LocalDateTime dateTime, String md5Hash,
            String path) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(photoBytes);
        this.image = Thumbnails.of(inputStream).scale(1).asBufferedImage();
        this.dateTime = dateTime;
        this.md5Hash = md5Hash;
        this.path = path;
    }

    /**
     * Getter method to get the photo's creation date (i.e. when the photo was
     * taken)
     * 
     * @return
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Getter method to get the file path of the photo
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter method to get the hash string of the photo
     * 
     * @return
     */
    public String getMd5Hash() {
        return md5Hash;
    }

    public String toString() {
        return toShortString() + ", " + path;
    }

    public String toShortString() {
        return dateTime.format(formatter) + ", " + md5Hash;
    }

    public byte[] toJpegBytes() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).scale(1).outputFormat("jpeg")
                    .toOutputStream(outStream);
        } catch (Exception e) {
            // This should not happen if image is valid.
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getThumbnail() {
        try {
           return Thumbnails.of(image).size(256,256).keepAspectRatio(true).asBufferedImage();
        }
        catch (Exception e) {
            // This should not happen if image is valid.
            e.printStackTrace();
        }
        return null;
    }
}
