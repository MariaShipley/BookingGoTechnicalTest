package com.mariashipley;


import org.junit.Test;

import static com.mariashipley.InputValidationUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputValidationUtilsTest
{
    @Test
    public void isPositiveInteger_PositiveInteger_True()
    {
        assertTrue(isPositiveInteger("2"));
    }

    @Test
    public void isPositiveInteger_NegativeInteger_False()
    {
        assertFalse(isPositiveInteger("-2"));
    }

    @Test
    public void isPositiveInteger_NotInteger_False()
    {
        assertFalse(isPositiveInteger("string"));
    }

    @Test
    public void isDouble_Double_True()
    {
        assertTrue(isDouble("1.2"));
    }

    @Test
    public void isDouble_NotDouble_False()
    {
        assertFalse(isDouble("string"));
    }

    @Test
    public void isAcceptableNumberOfArguments_CorrectNumOfArguments_True()
    {
        String[] fourArgs = new String[4];
        assertTrue(isAcceptableNumberOfArguments(fourArgs));
    }

    @Test
    public void isAcceptableNumberOfArguments_TooFewArguments_False()
    {
        String[] threeArgs = new String[3];
        assertFalse(isAcceptableNumberOfArguments(threeArgs));
    }

    @Test
    public void isAcceptableNumberOfArguments_TooManyArguments_False()
    {
        String[] sixArgs = new String[6];
        assertFalse(isAcceptableNumberOfArguments(sixArgs));
    }
}
