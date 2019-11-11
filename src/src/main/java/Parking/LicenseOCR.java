package Parking;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import net.coobird.thumbnailator.Thumbnails;

/**
 * LicenseOCR calls the OpenALPR API to read license plate from each photo.
 */
class LicenseOCR {
    /**
     * LicenseOCR takes in a photo and returns a car object.
     * @param photo
     * @return
     */
    public Car buildCarObject(String photoPath){
        //TODO call OpenALPR to get license and state
//    	String photoFilePathHardCoded = "src/photo.jpg"; // hard coding a photo file path for now
//    	String photoFilePath = photo.getPhotoFilePath();
        String license = "";
        String state = "";
        
        String[] carData = new String[2];
        carData = generateCarDataWithOpenALPR(photoPath);
        license = carData[0];
        state = carData[1];
        Car car = new Car(license, state);
        System.out.println("Car Object: " + car.getLicense() + "," + car.getState());
        
        return car;
    }
    
    
    
    private String[] generateCarDataWithOpenALPR(String filePath) {
    	String json_content = "";
    	String[] carData = new String[2];
    	
        try {
            String secret_key = "sk_b54c60658f3340d99b2d0531";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of("src/photo.jpg").scale(1).toOutputStream(outputStream);
            byte[] data = outputStream.toByteArray();
            File file = new File(filePath);

            // Encode file bytes to base64
            byte[] encoded = Base64.getEncoder().encode(data);

            // Setup the HTTPS connection to api.openalpr.com
            URL url = new URL("https://api.openalpr.com/v2/recognize_bytes?recognize_vehicle=1&country=us&secret_key=" + secret_key);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setFixedLengthStreamingMode(encoded.length);
            http.setDoOutput(true);

            // Send our Base64 content over the stream
            try(OutputStream os = http.getOutputStream()) {
                os.write(encoded);
            }
            int status_code = http.getResponseCode();
            if (status_code == 200) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                json_content = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    json_content += inputLine;
                in.close();
            }
            else {
                System.out.println("Got non-200 response: " + status_code);
            }
        }
        catch (MalformedURLException e) {
            System.out.println("Bad URL");
        }
        catch (IOException e) {
            System.out.println("Failed to open connection");
        }
//        catch (ImageProcessingException e) {
//            e.printStackTrace();
//        }
       
        carData = readJSONContent(json_content); //output of this is the license plate number
        
    	return carData;
    }

    private String[] readJSONContent(String jsonString) {
    	String[] carData = new String[2];
    	JSONObject obj = new JSONObject(jsonString);
		// gets values associated with "results" key
		JSONArray result = obj.getJSONArray("results");
		// gets values associated with "plate" key from the first index which has highest confidence
		String licensePlate = result.getJSONObject(0).getString("plate");
		String state = result.getJSONObject(0).getString("region").toUpperCase();
		carData[0] = licensePlate;
		carData[1] = state;
		return carData;
	}

	public LicenseOCR() {
    	
    }
	
	
	public static void main(String[] args) {
		LicenseOCR test = new LicenseOCR(); 
		test.buildCarObject("src/photo.jpg");
	}

}
