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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchEngine
{
    private static final int CONNECTION_TIMEOUT = 2000;

    private static final HashMap<String, Integer> carCapacity = new HashMap<>() {{
        put("STANDARD", 4);
        put("EXECUTIVE", 4);
        put("LUXURY", 4);
        put("PEOPLE_CARRIER", 6);
        put("LUXURY_PEOPLE_CARRIER", 6);
        put("MINIBUS", 16);
    }};

    /**
     * Searches for car options for the given locations and prints in descending price order.
     * @param supplierApi API URL for the supplier
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @param numPassengers Number of passengers
     * @return list of possible ride options for the given number of passengers
     */
    static List<RideOption> search(String supplierApi, Coordinate pickUpLocation, Coordinate dropOffLocation, int numPassengers)
    {
        URL url = buildQuery(supplierApi, pickUpLocation, dropOffLocation);
        if (url == null)
        {
            return null;
        }

        String response = makeApiCall(url);
        ApiResponse apiResponse = deserialize(response);
        if (apiResponse == null)
        {
            return null;
        }

        List<RideOption> rideOptions = apiResponse.rideOptions;
        List<RideOption> acceptableRideOptions = filterRidesByCapacity(rideOptions, numPassengers);
        Collections.sort(acceptableRideOptions, Collections.reverseOrder());

        return acceptableRideOptions;
    }

    /**
     * Searches all suppliers for car options for the given locations
     * @param supplier supplier of ride
     * @param ridesOptions list of ride options
     * @return list of ride options with the supplier set to the input supplier
     */
    private static List<RideOption> setSupplierOnList(String supplier, List<RideOption> ridesOptions)
    {
        if (ridesOptions == null)
        {
            return null;
        }

        for (RideOption ride : ridesOptions)
        {
            ride.setSupplier(supplier);
        }

        return ridesOptions;
    }

    /**
     * Searches all suppliers for car options for the given locations
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @param numPassengers Number of passengers
     * @return list of best possible ride options from all suppliers for the given number of passengers
     */
    static List<RideOption> searchAll(Coordinate pickUpLocation, Coordinate dropOffLocation, int numPassengers)
    {
        List<RideOption> davesRides = setSupplierOnList(SupplierInfo.DAVE_TAXI_NAME, search(SupplierInfo.DAVE_TAXI_API, pickUpLocation, dropOffLocation, numPassengers));
        List<RideOption> ericsRides = setSupplierOnList(SupplierInfo.ERIC_TAXI_NAME, search(SupplierInfo.ERIC_TAXI_API, pickUpLocation, dropOffLocation, numPassengers));
        List<RideOption> jeffsRides = setSupplierOnList(SupplierInfo.JEFF_TAXI_NAME, search(SupplierInfo.JEFF_TAXI_API, pickUpLocation, dropOffLocation, numPassengers));

        List<RideOption> allRides = new ArrayList<>();
        if (davesRides != null)
        {
            allRides.addAll(davesRides);
        }
        if (ericsRides != null)
        {
            allRides.addAll(ericsRides);
        }
        if (jeffsRides != null)
        {
            allRides.addAll(jeffsRides);
        }

        List<RideOption> bestRideOptions = filterCarTypeByPrice(allRides);

        return bestRideOptions;
    }

    /**
     * Filters a list of ride options to pick the lowest price ride option for each car type
     * @param rideOptions list of ride options
     * @return list of cheapest rideOptions per car type
     */
    static List<RideOption> filterCarTypeByPrice(List<RideOption> rideOptions)
    {
        HashMap<String, RideOption> bestCarTypePrice = new HashMap<>();

        for (RideOption ride : rideOptions)
        {
            if (bestCarTypePrice.get(ride.getCarType()) == null ||
                (bestCarTypePrice.get(ride.getCarType()).getPrice() > ride.getPrice()))
            {
                bestCarTypePrice.put(ride.getCarType(), ride);
            }
        }

        return new ArrayList<RideOption>(bestCarTypePrice.values());
    }

    /**
     * Constructs a URL of the API query with the given coordinates.
     * @param supplierApi API URL for the supplier
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @return URL of the query to access the API
     */
    public static URL buildQuery(String supplierApi, Coordinate pickUpLocation, Coordinate dropOffLocation)
    {
        StringBuilder query = new StringBuilder(supplierApi);
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

    public static List<RideOption> filterRidesByCapacity(List<RideOption> rideOptions, int numPassengers)
    {
        List<RideOption> acceptableRides = new ArrayList<>();
        for (RideOption option : rideOptions)
        {
            if (carCapacity.get(option.getCarType()) >= numPassengers)
            {
                acceptableRides.add(option);
            }
        }

        return acceptableRides;
    }

    /**
     * Prints ride options to the commandline
     * @param rideOptions list of ride options available
     */
    static void printRideOptions(List<RideOption> rideOptions)
    {
        if (rideOptions == null)
        {
            System.out.println("Something went wrong. Please try again.");
        }
        else if (rideOptions.isEmpty())
        {
            System.out.println("Sorry. There are no options available that match your query.");
        }
        else
        {
            for (RideOption option : rideOptions)
            {
                System.out.println(option);
            }
        }
    }
}
