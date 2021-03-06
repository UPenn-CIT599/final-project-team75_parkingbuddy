package Parking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * LicenseOCR uses the OpenALPR API to perform optical character recognition in order to read
 * license plate from each photo.
 */
class LicenseOCR {
  final static String urlStr =
      "https://api.openalpr.com/v2/recognize_bytes?recognize_" + "vehicle=1&country=us&secret_key=";
  final static String secretKey = "insert key here";

  /**
   * This method returns a car object by taking in a photo object and extracting the necessary data
   * from the photo. We call the OpenALPR API to get the license plate number and state.
   * 
   * @param photo (Photo)
   * @return Car
   */
  public Car getCarWithOpenALPR(Photo photo) {

    String jsonContent = "";

    try {
      // Encode file bytes to base64
      byte[] encoded = Base64.getEncoder().encode(photo.toJpegBytes());

      // Setup the HTTPS connection to api.openalpr.com
      URL url = new URL(urlStr + secretKey);
      URLConnection con = url.openConnection();
      HttpURLConnection http = (HttpURLConnection) con;
      http.setRequestMethod("POST"); // PUT is another valid option
      http.setFixedLengthStreamingMode(encoded.length);
      http.setDoOutput(true);

      // Send our Base64 content over the stream
      try (OutputStream os = http.getOutputStream()) {
        os.write(encoded);
      }
      int status_code = http.getResponseCode();
      if (status_code == 200) {
        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
          jsonContent += inputLine;
        in.close();
      } else {
        System.out.println("Got non-200 response: " + status_code);
      }
    } catch (MalformedURLException e) {
      System.out.println("Bad URL: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Failed to open connection: " + e.getMessage());
    }
    if (checkJSONForCar(jsonContent)) {
      return getCarFromJSON(jsonContent);
    }
    return null;
  }

  /**
   * This is a private helper method that checks if the image contains a vehicle. We check this by
   * looking at the JSON file generated by the OpenALPR API call, and seeing if the confidence of a
   * vehicle present in the image passes a certain threshold.
   * 
   * @param jsonContent String
   * @return boolean
   */
  private boolean checkJSONForCar(String jsonContent) {
    JSONObject obj = new JSONObject(jsonContent).optJSONObject("processing_time");
    double vehicle_confidence = obj.optDouble("vehicles");

    if (vehicle_confidence < 10) {
      return false;
    }
    return true;
  }

  /**
   * This is a helper method that takes in the JSON string file generated by the OpenALPR API call
   * and parses out the 2 pieces of information we need - the car license plate number and the
   * state, returned as a string array.
   * 
   * @param jsonString (String)
   * @return Car
   */
  private Car getCarFromJSON(String jsonString) {
    JSONObject obj = new JSONObject(jsonString);
    // gets values associated with "results" key
    JSONArray result = obj.getJSONArray("results");
    /**
     * gets values associated with "plate" key from the first index which has highest confidence
     */
    String licensePlate = result.getJSONObject(0).getString("plate");
    String state = result.getJSONObject(0).getString("region").toUpperCase();
    return new Car(state, licensePlate);
  }

  public static void main(String[] args) {
    LicenseOCR ocr = new LicenseOCR();
    Path filePath = Paths.get("src/test/java/Parking/CarFolder/");
    try {
      ArrayList<Photo> photos = PhotoFactory.createPhotos(filePath);
      for (Photo photo : photos) {
        System.out.println(photo);
        Car myCar = ocr.getCarWithOpenALPR(photo);
        System.out.println(myCar);
      }
    } catch (ParkingException e) {
      e.printStackTrace();
    }
  }
}
