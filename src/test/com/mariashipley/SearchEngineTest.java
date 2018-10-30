package test.com.mariashipley;

import main.com.mariashipley.Coordinate;
import main.com.mariashipley.RideOption;
import main.com.mariashipley.SupplierInfo;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static main.com.mariashipley.SearchEngine.buildQuery;
import static main.com.mariashipley.ResponseProcessing.filterRidesByCapacity;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEngineTest
{
    @Test
    void buildQuery_Coordinates_ReturnsCorrectQuery() throws MalformedURLException
    {
        URL expectedQuery = new URL("https://techtest.rideways.com/dave?pickup=1.234,5.678&dropoff=-12.5,13.57");

        Coordinate pickupLocation = new Coordinate(1.234, 5.678);
        Coordinate dropoffLocation = new Coordinate(-12.5, 13.57);

        assertEquals(buildQuery(SupplierInfo.DAVE_TAXI_API, pickupLocation, dropoffLocation), expectedQuery);
    }
}
