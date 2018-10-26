package test.com.mariashipley;

import main.com.mariashipley.Coordinate;
import main.com.mariashipley.RideOption;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static main.com.mariashipley.SearchEngine.buildQuery;
import static main.com.mariashipley.SearchEngine.filterRidesByCapacity;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEngineTest
{
    @Test
    void buildQuery_Coordinates_ReturnsCorrectQuery() throws MalformedURLException
    {
        URL expectedQuery = new URL("https://techtest.rideways.com/dave?pickup=1.234,5.678&dropoff=-12.5,13.57");

        Coordinate pickupLocation = new Coordinate(1.234, 5.678);
        Coordinate dropoffLocation = new Coordinate(-12.5, 13.57);

        assertEquals(buildQuery(pickupLocation, dropoffLocation), expectedQuery);
    }

    @Test
    void filterRidesByCapacity_ListOfRideOptions_ReturnsArrayOfAcceptableRides()
    {
        int numPassengers = 6;
        RideOption r1 = new RideOption("STANDARD", 1234); // 4 passengers
        RideOption r2 = new RideOption("MINIBUS", 5678); // 16 passengers
        RideOption r3 = new RideOption("LUXURY", 9810); // 4 passengers
        RideOption r4 = new RideOption("PEOPLE_CARRIER", 1112); // 6 passengers

        List<RideOption> expectedRides = Arrays.asList(r2, r4);

        List<RideOption> rides = Arrays.asList(r1, r2, r3, r4);

        assertEquals(expectedRides, filterRidesByCapacity(rides, numPassengers));
    }
}
