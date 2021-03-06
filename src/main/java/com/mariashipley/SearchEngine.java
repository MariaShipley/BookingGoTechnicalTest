package com.mariashipley;

import com.mariashipley.Models.ApiResponse;
import com.mariashipley.Models.Coordinate;
import com.mariashipley.Models.RideOption;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchEngine
{
    private static final int CONNECTION_TIMEOUT = 2000;

    private static final Logger LOGGER = Logger.getLogger(SearchEngine.class.getName());

    /**
     * Searches for car options for the given locations and prints in descending price order.
     * @param supplierApi API URL for the supplier
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @param numPassengers Number of passengers
     * @return list of possible ride options for the given number of passengers
     */
    public static List<RideOption> search(String supplierApi, Coordinate pickUpLocation, Coordinate dropOffLocation, int numPassengers)
    {
        URL url = buildQuery(supplierApi, pickUpLocation, dropOffLocation);
        if (url == null)
        {
            return null;
        }

        String response = makeApiCall(url);
        ApiResponse apiResponse = ResponseProcessing.deserialize(response);
        if (apiResponse == null)
        {
            return null;
        }

        List<RideOption> rideOptions = apiResponse.getRideOptions();
        List<RideOption> acceptableRideOptions = ResponseProcessing.filterRidesByCapacity(rideOptions, numPassengers);
        ResponseProcessing.setSupplierOnList(apiResponse.getSupplierId(), acceptableRideOptions);
        Collections.sort(acceptableRideOptions, Collections.reverseOrder());

        return acceptableRideOptions;
    }

    /**
     * Searches all suppliers for car options for the given locations
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @param numPassengers Number of passengers
     * @return list of best possible ride options from all suppliers for the given number of passengers
     */
    public static List<RideOption> searchAll(Coordinate pickUpLocation, Coordinate dropOffLocation, int numPassengers)
    {
        List<RideOption> davesRides = search(SupplierInfo.DAVE_TAXI_API, pickUpLocation, dropOffLocation, numPassengers);
        List<RideOption> ericsRides = search(SupplierInfo.ERIC_TAXI_API, pickUpLocation, dropOffLocation, numPassengers);
        List<RideOption> jeffsRides = search(SupplierInfo.JEFF_TAXI_API, pickUpLocation, dropOffLocation, numPassengers);

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

        List<RideOption> bestRideOptions = ResponseProcessing.filterCarTypeByPrice(allRides);
        Collections.sort(bestRideOptions, Collections.reverseOrder());

        return bestRideOptions;
    }

    /**
     * Constructs a URL of the API query with the given coordinates.
     * @param supplierApi API URL for the supplier
     * @param pickUpLocation Coordinates of the pick-up location
     * @param dropOffLocation Coordinates of the drop-off location
     * @return URL of the query to access the API
     */
    static URL buildQuery(String supplierApi, Coordinate pickUpLocation, Coordinate dropOffLocation)
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
            LOGGER.log(Level.WARNING, "Could not build URL for API query.");

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
                return ResponseProcessing.inputStreamToString(connection.getInputStream());
            }
        }
        catch (SocketTimeoutException timeoutException)
        {
            LOGGER.log(Level.WARNING, "Connection timed-out: Re-submit your request.");
        }
        catch (IOException e)
        {
            LOGGER.log(Level.WARNING, "Encountered a problem: please try again.");
        }

        return null;
    }

    /**
     * Prints ride options to the commandline
     * @param rideOptions list of ride options available
     */
    public static void printRideOptions(List<RideOption> rideOptions, boolean printSupplier)
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
                if (printSupplier)
                {
                    System.out.println(option);
                }
                else
                {
                    System.out.println(option.basicInfoString());
                }
            }
        }
    }
}
