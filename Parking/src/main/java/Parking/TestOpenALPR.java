import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Base64;
import org.json.*;

class TestOpenALPR {

    public String ParseData() {
        String json_content = "";
        try {
            String secret_key = "sk_b54c60658f3340d99b2d0531";

            // Read image file to byte array
            Path path = Paths.get("src/test.jpg");
            byte[] data = Files.readAllBytes(path);

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
        return json_content;
    }


    public static void main(String[] args) {
        TestOpenALPR test = new TestOpenALPR();
        String jsonStr = test.ParseData(); // json_content
        System.out.print(jsonStr); // prints json_content

        JSONObject obj = new JSONObject(jsonStr);
        // gets values associated with "results" key
        JSONArray result = obj.getJSONArray("results");
        // gets values associated with "plate" key from the first index which has highest confidence
        String licensePlate = result.getJSONObject(0).getString("plate");
        System.out.print("\nLicense plate number: " + licensePlate);

        }
    }
