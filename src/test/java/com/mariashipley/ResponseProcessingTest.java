package com.mariashipley;

import com.mariashipley.Models.RideOption;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResponseProcessingTest
{
    @Test
    public void filterRidesByCapacity_ListOfRideOptions_ReturnsArrayOfAcceptableRides()
    {
        int numPassengers = 6;
        RideOption r1 = new RideOption("STANDARD", 1234); // 4 passengers
        RideOption r2 = new RideOption("MINIBUS", 5678); // 16 passengers
        RideOption r3 = new RideOption("LUXURY", 9810); // 4 passengers
        RideOption r4 = new RideOption("PEOPLE_CARRIER", 1112); // 6 passengers

        List<RideOption> expectedRides = Arrays.asList(r2, r4);

        List<RideOption> rides = Arrays.asList(r1, r2, r3, r4);

        assertEquals(expectedRides, ResponseProcessing.filterRidesByCapacity(rides, numPassengers));
    }

    @Test
    public void filterCarTypeByPrice_ListOfRideOptions_ReturnsCheapestOfEachCarType()
    {
        RideOption r1 = new RideOption("MINIBUS", 5678, "DAVE");
        RideOption r2 = new RideOption("MINIBUS", 5677, "JEFF");
        RideOption r3 = new RideOption("MINIBUS", 5679, "ERIC");

        List<RideOption> expectedRides = Collections.singletonList(r2);

        List<RideOption> rides = Arrays.asList(r1, r2, r3);

        assertEquals(expectedRides, ResponseProcessing.filterCarTypeByPrice(rides));
    }

    @Test
    public void filterCarTypeByPrice_ListOfRideOptions_ReturnsCorrectLengthList()
    {
        RideOption r1 = new RideOption("STANDARD", 1000, "DAVE");
        RideOption r2 = new RideOption("MINIBUS", 5678, "DAVE");
        RideOption r3 = new RideOption("MINIBUS", 5677, "JEFF");
        RideOption r4 = new RideOption("MINIBUS", 5679, "ERIC");
        RideOption r5 = new RideOption("LUXURY", 900, "DAVE");

        List<RideOption> rides = Arrays.asList(r1, r2, r3, r4, r5);

        int expectedListLength = 3;

        int actualListLength = ResponseProcessing.filterCarTypeByPrice(rides).size();

        assertEquals(expectedListLength, actualListLength);
    }
}
