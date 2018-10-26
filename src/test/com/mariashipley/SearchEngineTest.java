package test.com.mariashipley;

import main.com.mariashipley.Coordinate;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static main.com.mariashipley.SearchEngine.buildQuery;
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
}
