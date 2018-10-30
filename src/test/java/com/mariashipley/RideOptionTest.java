package com.mariashipley;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RideOptionTest
{
    private RideOption rideOption = new RideOption("STANDARD", 300);

    @Test
    void toString_RideOption_ConvertsRideOptionToCorrectFormat()
    {
        String expectedString = "STANDARD - 300";

        assertEquals(expectedString, rideOption.toString());
    }

    @Test
    void compareTo_FirstRideOptionIsMoreExpensive_Positive()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 299));
        assertEquals(1, result);
    }

    @Test
    void compareTo_FirstRideOptionIsCheaper_Negative()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 301));
        assertEquals(-1, result);
    }

    @Test
    void compareTo_BothRideOptionsCostTheSame_Zero()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 300));
        assertEquals(0, result);
    }
}
