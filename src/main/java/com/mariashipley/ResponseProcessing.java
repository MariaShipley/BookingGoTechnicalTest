package com.mariashipley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResponseProcessing
{
    private static final HashMap<String, Integer> carCapacity = new HashMap<>() {{
        put("STANDARD", 4);
        put("EXECUTIVE", 4);
        put("LUXURY", 4);
        put("PEOPLE_CARRIER", 6);
        put("LUXURY_PEOPLE_CARRIER", 6);
        put("MINIBUS", 16);
    }};

    /**
     * Converts given input stream into a string.
     * @param inputStream The input stream to be converted
     * @return string conversion of input stream
     */
    static String inputStreamToString(InputStream inputStream) throws IOException
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
    static ApiResponse deserialize(String jsonString)
    {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.fromJson(jsonString, ApiResponse.class);
    }

    /**
     * Filters a list of ride options to remove rides with too low a capacity
     * @param rideOptions list of ride options
     * @param numPassengers number of passengers
     * @return list of rides available with capacity to fit the given number of passengers
     */
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
     * Filters a list of ride options to pick the lowest price ride option for each car type
     * @param rideOptions list of ride options
     * @return list of cheapest rideOptions per car type
     */
    public static List<RideOption> filterCarTypeByPrice(List<RideOption> rideOptions)
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
     * Searches all suppliers for car options for the given locations
     * @param supplier supplier of ride
     * @param ridesOptions list of ride options
     * @return list of ride options with the supplier set to the input supplier
     */
    static List<RideOption> setSupplierOnList(String supplier, List<RideOption> ridesOptions)
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
}
