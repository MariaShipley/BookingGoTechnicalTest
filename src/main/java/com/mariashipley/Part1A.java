package com.mariashipley;

import com.mariashipley.Models.Coordinate;
import com.mariashipley.Models.RideOption;

import java.util.List;

public class Part1A
{
    public static void main(String[] args)
    {
        if (!InputValidationUtils.isInputValid(args))
        {
            InputValidationUtils.printExceptionMessage();
            System.exit(0);
        }

        Coordinate pickUpLocation = new Coordinate(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        Coordinate dropOffLocation = new Coordinate(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        int numPassengers = 0;

        if (args.length == 5)
        {
            numPassengers = Integer.parseInt(args[4]);
        }

        List<RideOption> rideOptions = SearchEngine.search(SupplierInfo.DAVE_TAXI_API, pickUpLocation, dropOffLocation, numPassengers);

        SearchEngine.printRideOptions(rideOptions, false);
    }
}
