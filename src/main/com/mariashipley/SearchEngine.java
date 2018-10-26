package main.com.mariashipley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchEngine
{
    private static final String DAVE_TAXI_API = "https://techtest.rideways.com/dave";
    private static final int CONNECTION_TIMEOUT = 2000;

    private static final HashMap<String, Integer> carCapacity = new HashMap<>() {{
        put("STANDARD", 4);
        put("EXECUTIVE", 4);
        put("LUXURY", 4);
        put("PEOPLE CARRIER", 6);
        put("LUXURY_PEOPLE CARRIER", 6);
        put("MINIBUS", 16);
    }};

    /**
     * Searches for car options for the given locations and prints in descending price order.
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     */
    static void search(Coordinate pickUpLocation, Coordinate dropOffLocation)
    {
        URL url = buildQuery(pickUpLocation, dropOffLocation);
        if (url == null)
        {
            return;
        }

        String response = makeApiCall(url);
        ApiResponse apiResponse = deserialize(response);
        if (apiResponse == null)
        {
            return;
        }

        List<RideOption> rideOptions = apiResponse.rideOptions;
        if (rideOptions.isEmpty())
        {
            System.out.println("Sorry. There are no options available that match your query.");
        }

        Collections.sort(rideOptions, Collections.reverseOrder());
        for (RideOption option : rideOptions)
        {
            System.out.println(option);
        }
    }

    /**
     * Constructs a URL of the API query with the given coordinates.
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @return URL of the query to access the API
     */
    public static URL buildQuery(Coordinate pickUpLocation, Coordinate dropOffLocation)
    {
        StringBuilder query = new StringBuilder(DAVE_TAXI_API);
        query.append("?pickup=");
        query.append(pickUpLocation.getLatitude());
        query.append(",");
        query.append(pickUpLocation.getLongitude());
        query.append("&dropoff=");
        query.append(dropOffLocation.getLatitude());
        query.append(",");
        query.append(dropOffLocation.getLongitude());

        try
        {
            return new URL(query.toString());
        }
        catch (MalformedURLException e)
        {
            System.out.println("Could not build URL for API query.");
            return null;
        }
    }

    /**
     * Makes the HTTP call to the API.
     * @param url The url of the API query
     * @return string of API Response
     */
    private static String makeApiCall(URL url)
    {
        try
        {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            int responseCode = connection.getResponseCode();

            if (responseCode == 200)
            {
                return inputStreamToString(connection.getInputStream());
            }
            else
            {
                System.out.println("Error code: " + responseCode);
                if (responseCode == 400)
                {
                    System.out.println("Bad Request: Make sure your request was entered in the correct format.");
                }
                else if (responseCode == 500)
                {
                    System.out.println("Internal Server error: Wait a few seconds and re-submit your request");
                }
            }
        }
        catch (SocketTimeoutException timeoutException)
        {
            System.out.println("Connection timed-out: Re-submit your request.");
        }
        catch (IOException e)
        {
            System.out.println("Encountered a problem: please try again.");
        }

        return null;
    }

    /**
     * Converts given input stream into a string.
     * @param inputStream The input stream to be converted
     * @return string conversion of input stream
     */
    private static String inputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }

    /**
     * Deserializes the JSON string into and ApiResponse object
     * @param jsonString The JSON string to be converted
     * @return ApiResponse object
     */
    private static ApiResponse deserialize(String jsonString)
    {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.fromJson(jsonString, ApiResponse.class);
    }
}
