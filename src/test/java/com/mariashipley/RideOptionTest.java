package com.mariashipley;

import com.mariashipley.Models.RideOption;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RideOptionTest
{
    private RideOption rideOption = new RideOption("STANDARD", 300);

    @Test
    public void toString_RideOption_ConvertsRideOptionToCorrectFormat()
    {
        String expectedString = "STANDARD - 300";

        assertEquals(expectedString, rideOption.toString());
    }

    @Test
    public void compareTo_FirstRideOptionIsMoreExpensive_Positive()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 299));
        assertEquals(1, result);
    }

    @Test
    public void compareTo_FirstRideOptionIsCheaper_Negative()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 301));
        assertEquals(-1, result);
    }

    @Test
    public void compareTo_BothRideOptionsCostTheSame_Zero()
    {
        int result = rideOption.compareTo(new RideOption("STANDARD", 300));
        assertEquals(0, result);
    }
}
