package Parking;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import java.util.List;

/**
 * This class reads the image files and extracts the EXIF metadata required to construct a Photo
 * object from each image.
 *
 */
public class PhotoFactory {
  final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

  /**
   * This static method creates an ArrayList of Photo objects from each image file in the designated
   * folder.
   * 
   * @param path (Path)
   * @return ArrayList of Photos
   */
  public static ArrayList<Photo> createPhotos(Path path) throws ParkingException {
    // get paths to the image files in the folder
    File[] files = path.toFile().listFiles();
    if (files == null || files.length == 0) {
      throw new ParkingException("Invalid dir: " + path.toString());
    }
    return createPhotos(Arrays.asList(files));
  }

  /**
   * This static method creates an ArrayList of Photo objects from a List of image files.
   * 
   * @param List of Files
   * @return ArrayList of Photos
   */
  public static ArrayList<Photo> createPhotos(List<File> files) throws ParkingException {
    // initialize ArrayList of photo objects
    ArrayList<Photo> photos = new ArrayList<Photo>();
    // get paths to the image files in the folder
    if (files == null || files.size() == 0) {
      throw new ParkingException("Invalid files: " + files);
    }
    ArrayList<File> filteredFiles = new ArrayList<File>();
    for (File file : files) {
      String ext = FilenameUtils.getExtension(file.getName());
      if (!ext.equals("jpeg") && !ext.equals("jpg")) {
        continue;
      }
      filteredFiles.add(file);
    }
    if (filteredFiles.size() == 0) {
      throw new ParkingException("No valid photo files: " + files);
    }

    // iterate through each image file in the folder to create a photo object
    for (File file : filteredFiles) {
      try {
        photos.add(createPhoto(file));
      } catch (ParkingException e) {
        System.out.println(e.getMessage());
      }
    }
    return photos;
  }

  /**
   * This method creates a Photo object from an image file. Used to create individual photo objects
   * from the File argument.
   * 
   * @param file (File)
   * @return photo (Photo)
   */
  public static Photo createPhoto(File file) throws ParkingException {
    return createPhoto(file.toPath());
  }

  /**
   * This method creates a Photo object from a given file path.
   * 
   * @param file (File)
   * @return path (Path)
   */
  public static Photo createPhoto(Path path) throws ParkingException {
    Photo photo = null;
    try {
      // create inputstream from bytes
      byte[] bytes = Files.readAllBytes(path);
      InputStream inputStream = new ByteArrayInputStream(bytes);

      // Make the image smaller while keeping aspect ratio.
      BufferedImage image =
          Thumbnails.of(inputStream).size(1024, 1024).keepAspectRatio(true).asBufferedImage();
      inputStream.reset();
      LocalDateTime dateTime = getDateTime(inputStream);
      inputStream.reset();
      String md5Hash = DigestUtils.md5Hex(inputStream).toUpperCase();

      // create Photo object
      photo = new Photo(image, dateTime, md5Hash, path.toString());
    } catch (IOException e) {
      throw new ParkingException("Unable to read Photo file: " + e.getMessage());
    }

    return photo;
  }

  /**
   * This is a private method that extracts the original date of the files from a folder by
   * extracting exif data and storing them in an ArrayList of Strings.
   * 
   * Note: To get date from LocalDateTime object: formattedDate.toLocalDate() To get time from
   * LocalDateTime object: formattedDate.toLocalTime()
   * 
   * @param file
   * @return
   */
  private static LocalDateTime getDateTime(InputStream inputStream) throws ParkingException {
    LocalDateTime dateTime = null;
    try {
      // getting Metadata inside an image file
      Metadata metadata;
      metadata = ImageMetadataReader.readMetadata(inputStream);
      ExifSubIFDDirectory dir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

      // we only care about the data and time metadata
      String dateStr = dir.getString(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
      dateTime = LocalDateTime.parse(dateStr, formatter);
    } catch (Exception e) {
      throw new ParkingException("Unable to extract Photo metadata: " + e.getMessage());
    }

    return dateTime;
  }

  public static void main(String[] args) {
    Path filePath = Paths.get("src/test/java/Parking/MultipleImagesFolder/");
    try {
      ArrayList<Photo> photos = PhotoFactory.createPhotos(filePath);
      for (Photo photo : photos) {
        System.out.println(photo);
      }
    } catch (ParkingException e) {
      e.printStackTrace();
    }
  }
}
